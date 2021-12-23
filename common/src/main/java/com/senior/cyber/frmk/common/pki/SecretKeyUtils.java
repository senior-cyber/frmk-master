package com.senior.cyber.frmk.common.pki;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

public class SecretKeyUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static SecretKey extractSecretKey(ECPrivateKey privateKey, ECPublicKey publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        byte[] secretData = keyAgreement.generateSecret();
        int keyLength = secretData.length * 8;
        if (keyLength == 256) {
            return new SecretKeySpec(secretData, 0, secretData.length, "AES");
        } else {
            throw new IllegalArgumentException("not support key size " + keyLength);
        }
    }

    public static String hashText(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
    }

    public static String hashText(String text, SecretKey secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encryptText(SecretKey secretKey, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int length = 16;
        byte[] iv = RANDOM.generateSeed(length);
        Cipher cipher = Cipher.getInstance("AES_256/GCM/NoPadding");
        GCMParameterSpec gcm = new GCMParameterSpec(iv.length * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcm);
        byte[] cipherData = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(iv) + "." + Base64.getEncoder().encodeToString(cipherData);
    }

    public static String decryptText(SecretKey secretKey, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int dotIndex = text.indexOf('.');
        String ivText = text.substring(0, dotIndex);
        Cipher cipher = Cipher.getInstance("AES_256/GCM/NoPadding");
        byte[] iv = Base64.getDecoder().decode(ivText);
        GCMParameterSpec gcm = new GCMParameterSpec(iv.length * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcm);
        byte[] textData = cipher.doFinal(Base64.getDecoder().decode(text.substring(dotIndex + 1)));
        return new String(textData, StandardCharsets.UTF_8);
    }

}
