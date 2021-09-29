package com.senior.cyber.frmk.common.pki;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Base64;

public abstract class Jwt<T> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    protected T payload;
    protected Header header;
    protected String headerText;
    protected String payloadText;

    protected Class<T> payloadClass;

    protected String signature;

    protected Jwt(Class<T> payloadClass, Algorithm algorithm) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.payloadClass = payloadClass;
        this.header = new Header();
        this.header.algorithm = algorithm;
        this.header.type = "JWT";
        byte[] headerByte = gson.toJson(header).getBytes(StandardCharsets.UTF_8);
        this.headerText = Base64.getUrlEncoder().withoutPadding().encodeToString(headerByte);
    }

    public static String pem(PrivateKey privateKey) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(privateKey);
        }
        return pem.toString();
    }

    public static String pem(PublicKey privateKey) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(privateKey);
        }
        return pem.toString();
    }

    public static String pem(KeyPair keyPair) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(keyPair);
        }
        return pem.toString();
    }

    public static String pem(X509Certificate certificate) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(certificate);
        }
        return pem.toString();
    }

    public static <P, J extends Jwt<P>> J fromString(Class<J> jwtClass, String token) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String[] segments = StringUtils.split(token, ".");
        String headerText = segments[0];
        byte[] headerByte = Base64.getUrlDecoder().decode(headerText);
        Header header = gson.fromJson(new String(headerByte, StandardCharsets.UTF_8), Header.class);
        Algorithm algorithm = header.getAlgorithm();
        J jwt = jwtClass.getDeclaredConstructor(Algorithm.class).newInstance(algorithm);
        jwt.headerText = headerText;
        jwt.payloadText = segments[1];
        jwt.signature = segments[2];
        jwt.payload = gson.fromJson(new String(Base64.getUrlDecoder().decode(jwt.payloadText), StandardCharsets.UTF_8), jwt.payloadClass);
        jwt.header = gson.fromJson(new String(headerByte, StandardCharsets.UTF_8), Header.class);
        if (!"JWT".equals(jwt.header.type)) {
            throw new IllegalArgumentException("Not supported alg " + jwt.header.algorithm + " and typ " + jwt.header.type);
        }

        return jwt;
    }

    protected static int countPadding(byte[] bytes, int fromIndex, int toIndex) {
        int padding = 0;
        while (fromIndex + padding < toIndex && bytes[fromIndex + padding] == 0) {
            padding++;
        }
        return (bytes[fromIndex + padding] & 0xff) > 0x7f ? padding - 1 : padding;
    }

    protected static byte[] JOSEToDER(int ecNumberSize, byte[] joseSignature) throws SignatureException {
        if (joseSignature.length != ecNumberSize * 2) {
            throw new SignatureException("Invalid JOSE signature format.");
        }

        // Retrieve R and S number's length and padding.
        int rPadding = countPadding(joseSignature, 0, ecNumberSize);
        int sPadding = countPadding(joseSignature, ecNumberSize, joseSignature.length);
        int rLength = ecNumberSize - rPadding;
        int sLength = ecNumberSize - sPadding;

        int length = 2 + rLength + 2 + sLength;
        if (length > 255) {
            throw new SignatureException("Invalid JOSE signature format.");
        }

        final byte[] derSignature;
        int offset;
        if (length > 0x7f) {
            derSignature = new byte[3 + length];
            derSignature[1] = (byte) 0x81;
            offset = 2;
        } else {
            derSignature = new byte[2 + length];
            offset = 1;
        }

        // DER Structure: http://crypto.stackexchange.com/a/1797
        // Header with signature length info
        derSignature[0] = (byte) 0x30;
        derSignature[offset++] = (byte) (length & 0xff);

        // Header with "min R" number length
        derSignature[offset++] = (byte) 0x02;
        derSignature[offset++] = (byte) rLength;

        // R number
        if (rPadding < 0) {
            //Sign
            derSignature[offset++] = (byte) 0x00;
            System.arraycopy(joseSignature, 0, derSignature, offset, ecNumberSize);
            offset += ecNumberSize;
        } else {
            int copyLength = Math.min(ecNumberSize, rLength);
            System.arraycopy(joseSignature, rPadding, derSignature, offset, copyLength);
            offset += copyLength;
        }

        // Header with "min S" number length
        derSignature[offset++] = (byte) 0x02;
        derSignature[offset++] = (byte) sLength;

        // S number
        if (sPadding < 0) {
            //Sign
            derSignature[offset++] = (byte) 0x00;
            System.arraycopy(joseSignature, ecNumberSize, derSignature, offset, ecNumberSize);
        } else {
            System.arraycopy(joseSignature, ecNumberSize + sPadding, derSignature, offset, Math.min(ecNumberSize, sLength));
        }

        return derSignature;
    }

    protected static byte[] DERToJOSE(int ecNumberSize, byte[] derSignature) throws SignatureException {
        // DER Structure: http://crypto.stackexchange.com/a/1797
        boolean derEncoded = derSignature[0] == 0x30 && derSignature.length != ecNumberSize * 2;
        if (!derEncoded) {
            throw new SignatureException("Invalid DER signature format.");
        }

        final byte[] joseSignature = new byte[ecNumberSize * 2];

        //Skip 0x30
        int offset = 1;
        if (derSignature[1] == (byte) 0x81) {
            //Skip sign
            offset++;
        }

        //Convert to unsigned. Should match DER length - offset
        int encodedLength = derSignature[offset++] & 0xff;
        if (encodedLength != derSignature.length - offset) {
            throw new SignatureException("Invalid DER signature format.");
        }

        //Skip 0x02
        offset++;

        //Obtain R number length (Includes padding) and skip it
        int rLength = derSignature[offset++];
        if (rLength > ecNumberSize + 1) {
            throw new SignatureException("Invalid DER signature format.");
        }
        int rPadding = ecNumberSize - rLength;
        //Retrieve R number
        System.arraycopy(derSignature, offset + Math.max(-rPadding, 0), joseSignature, Math.max(rPadding, 0), rLength + Math.min(rPadding, 0));

        //Skip R number and 0x02
        offset += rLength + 1;

        //Obtain S number length. (Includes padding)
        int sLength = derSignature[offset++];
        if (sLength > ecNumberSize + 1) {
            throw new SignatureException("Invalid DER signature format.");
        }
        int sPadding = ecNumberSize - sLength;
        //Retrieve R number
        System.arraycopy(derSignature, offset + Math.max(-sPadding, 0), joseSignature, ecNumberSize + Math.max(sPadding, 0), sLength + Math.min(sPadding, 0));

        return joseSignature;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public boolean verifyUsing(RSAPublicKey publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (Algorithm.RS256 == this.header.algorithm ||
                Algorithm.RS384 == this.header.algorithm ||
                Algorithm.RS512 == this.header.algorithm ||
                Algorithm.PS256 == this.header.algorithm ||
                Algorithm.PS384 == this.header.algorithm) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support rsa private key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        byte[] payloadByte = Base64.getUrlDecoder().decode(payloadText);
        payload = gson.fromJson(new String(payloadByte, StandardCharsets.UTF_8), payloadClass);
        Signature verifier = null;
        if (Algorithm.RS256 == header.algorithm || Algorithm.RS384 == header.algorithm || Algorithm.RS512 == header.algorithm) {
            verifier = Signature.getInstance("SHA" + header.algorithm.size + "withRSA", BouncyCastleProvider.PROVIDER_NAME);
        } else if (Algorithm.PS256 == header.algorithm || Algorithm.PS384 == header.algorithm) {
            verifier = Signature.getInstance("SHA" + header.algorithm.size + "withRSA/PSS", BouncyCastleProvider.PROVIDER_NAME);
            verifier.setParameter(new PSSParameterSpec("SHA-" + header.algorithm.size, "MGF1", MGF1ParameterSpec.SHA256, header.algorithm.size / 8, 1));
        }
        verifier.initVerify(publicKey);
        verifier.update((headerText + "." + payloadText).getBytes(StandardCharsets.UTF_8));
        if (verifier.verify(Base64.getUrlDecoder().decode(signature))) {
            return true;
        }
        return false;
    }

    public boolean verifyUsing(ECPublicKey publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {
        if (Algorithm.ES256 == this.header.algorithm ||
                Algorithm.ES384 == this.header.algorithm ||
                Algorithm.ES512 == this.header.algorithm) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support rsa private key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        byte[] payloadByte = Base64.getUrlDecoder().decode(payloadText);
        payload = gson.fromJson(new String(payloadByte, StandardCharsets.UTF_8), payloadClass);
        Signature verifier = Signature.getInstance("SHA" + header.algorithm.size + "withECDSA", BouncyCastleProvider.PROVIDER_NAME);
        verifier.initVerify(publicKey);
        verifier.update((headerText + "." + payloadText).getBytes(StandardCharsets.UTF_8));
        byte[] signature = JOSEToDER(header.algorithm.size / 8, Base64.getUrlDecoder().decode(this.signature));
        if (verifier.verify(signature)) {
            return true;
        }
        return false;
    }

    public boolean verifyUsing(SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        if (Algorithm.HS256 == this.header.algorithm ||
                Algorithm.HS384 == this.header.algorithm ||
                Algorithm.HS512 == this.header.algorithm) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support rsa private key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        byte[] payloadByte = Base64.getUrlDecoder().decode(payloadText);
        payload = gson.fromJson(new String(payloadByte, StandardCharsets.UTF_8), payloadClass);
        Mac hmac = Mac.getInstance("HmacSHA" + header.algorithm.size, BouncyCastleProvider.PROVIDER_NAME);
        hmac.init(secretKey);
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal((headerText + "." + payloadText).getBytes(StandardCharsets.UTF_8)));
        if (this.signature.equals(signature)) {
            return true;
        }
        return false;
    }

    public String signUsing(RSAPrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (Algorithm.RS256 == this.header.algorithm ||
                Algorithm.RS384 == this.header.algorithm ||
                Algorithm.RS512 == this.header.algorithm ||
                Algorithm.PS256 == this.header.algorithm ||
                Algorithm.PS384 == this.header.algorithm) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support rsa private key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Algorithm algorithm = header.algorithm;
        this.payloadText = Base64.getUrlEncoder().withoutPadding().encodeToString(gson.toJson(this.payload).getBytes(StandardCharsets.UTF_8));
        Signature issuer = null;
        if (Algorithm.RS256 == algorithm || Algorithm.RS384 == algorithm || Algorithm.RS512 == algorithm) {
            issuer = Signature.getInstance("SHA" + algorithm.size + "withRSA", BouncyCastleProvider.PROVIDER_NAME);
        } else if (Algorithm.PS256 == algorithm || Algorithm.PS384 == algorithm) {
            issuer = Signature.getInstance("SHA" + algorithm.size + "withRSA/PSS", BouncyCastleProvider.PROVIDER_NAME);
            issuer.setParameter(new PSSParameterSpec("SHA-" + algorithm.size, "MGF1", MGF1ParameterSpec.SHA256, algorithm.size / 8, 1));
        }
        issuer.initSign(privateKey);
        issuer.update((this.headerText + "." + this.payloadText).getBytes(StandardCharsets.UTF_8));
        byte[] signature = issuer.sign();
        this.signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        return this.headerText + "." + this.payloadText + "." + StringUtils.trim(this.signature);
    }

    public String signUsing(ECPrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
        if (Algorithm.ES256 == this.header.algorithm ||
                Algorithm.ES384 == this.header.algorithm ||
                Algorithm.ES512 == this.header.algorithm
        ) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support rsa private key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Algorithm algorithm = header.algorithm;
        this.payloadText = Base64.getUrlEncoder().withoutPadding().encodeToString(gson.toJson(this.payload).getBytes(StandardCharsets.UTF_8));
        Signature issuer = Signature.getInstance("SHA" + algorithm.size + "withECDSA", BouncyCastleProvider.PROVIDER_NAME);
        issuer.initSign(privateKey);
        issuer.update((this.headerText + "." + this.payloadText).getBytes(StandardCharsets.UTF_8));
        byte[] signature = DERToJOSE(algorithm.size / 8, issuer.sign());
        this.signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        return this.headerText + "." + this.payloadText + "." + StringUtils.trim(this.signature);
    }

    public String signUsing(SecretKey secretKey) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        if (Algorithm.HS256 == this.header.algorithm ||
                Algorithm.HS384 == this.header.algorithm ||
                Algorithm.HS512 == this.header.algorithm) {
        } else {
            throw new IllegalArgumentException(this.header.algorithm + " does not support secret key");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Algorithm algorithm = header.algorithm;
        this.payloadText = Base64.getUrlEncoder().withoutPadding().encodeToString(gson.toJson(this.payload).getBytes(StandardCharsets.UTF_8));
        Mac hmac = Mac.getInstance("HmacSHA" + algorithm.size, BouncyCastleProvider.PROVIDER_NAME);
        hmac.init(secretKey);
        byte[] signature = hmac.doFinal((this.headerText + "." + this.payloadText).getBytes(StandardCharsets.UTF_8));
        this.signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        return this.headerText + "." + this.payloadText + "." + StringUtils.trim(this.signature);
    }

    public enum Algorithm {
        HS256(256), HS384(384), HS512(512),
        RS256(256), RS384(384), RS512(512),
        ES256(256), ES384(384), ES512(512),
        PS256(256), PS384(384);

        private final int size;

        Algorithm(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

    }

    public static class Header {

        @Expose
        @SerializedName("alg")
        private Algorithm algorithm;

        @Expose
        @SerializedName("typ")
        private String type;

        public Algorithm getAlgorithm() {
            return algorithm;
        }

        public String getType() {
            return type;
        }

    }

}
