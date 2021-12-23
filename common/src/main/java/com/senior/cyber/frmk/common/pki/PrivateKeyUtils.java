package com.senior.cyber.frmk.common.pki;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

public class PrivateKeyUtils {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static PrivateKey read(String pem) throws IOException {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object objectHolder = parser.readObject();
            if (objectHolder instanceof PEMKeyPair) {
                PEMKeyPair holder = (PEMKeyPair) objectHolder;
                return new JcaPEMKeyConverter().getPrivateKey(holder.getPrivateKeyInfo());
            } else if (objectHolder instanceof PrivateKeyInfo) {
                PrivateKeyInfo holder = (PrivateKeyInfo) objectHolder;
                return new JcaPEMKeyConverter().getPrivateKey(holder);
            } else {
                throw new java.lang.UnsupportedOperationException(objectHolder.getClass().getName());
            }
        }
    }

    public static PrivateKey read(File pem) throws IOException {
        return read(FileUtils.readFileToString(pem, StandardCharsets.UTF_8));
    }

    public static String write(PrivateKey privateKey) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(new JcaPKCS8Generator(privateKey, null));
        }
        return pem.toString();
    }

    public static String signText(PrivateKey privateKey, String text) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = null;
        if (privateKey instanceof RSAPrivateKey) {
            signature = Signature.getInstance("SHA256withRSA");
        } else if (privateKey instanceof ECPrivateKey) {
            signature = Signature.getInstance("SHA256withECDSA");
        } else {
            throw new IllegalArgumentException(privateKey.getClass().getName() + " is not supported");
        }
        signature.initSign(privateKey, new SecureRandom());
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign()) + "." + text;
    }

    public static String decryptText(PrivateKey privateKey, String text) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        if (privateKey instanceof RSAPrivateKey) {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] textData = cipher.doFinal(Base64.getDecoder().decode(text));
            return new String(textData, StandardCharsets.UTF_8);
        } else if (privateKey instanceof ECPrivateKey) {
            int dotIndex = text.indexOf('.');
            byte[] iv = Base64.getDecoder().decode(text.substring(0, dotIndex));
            byte[] derivation = iv.clone();
            byte[] encoding = iv.clone();
            int length = iv.length;
            Cipher cipher = Cipher.getInstance("ECIESwithSHA256andAES-CBC", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, new IESParameterSpec(derivation, encoding, length * 8, length * 8, iv, false));
            byte[] textData = cipher.doFinal(Base64.getDecoder().decode(text.substring(dotIndex + 1)));
            return new String(textData, StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException(privateKey.getClass().getName() + " is not supported");
        }
    }

}
