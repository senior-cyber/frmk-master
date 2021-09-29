package com.senior.cyber.frmk.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

public class X509CertificateTypeAdapter extends TypeAdapter<X509Certificate> {

    @Override
    public void write(JsonWriter out, X509Certificate value) throws IOException {
        if (value != null) {
            StringWriter pem = new StringWriter();
            try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
                writer.writeObject(value);
            }
            out.value(pem.toString());
        } else {
            out.nullValue();
        }
    }

    @Override
    public X509Certificate read(JsonReader in) throws IOException {
        String pem = in.nextString();
        try (StringReader reader = new StringReader(pem)) {
            try (PEMParser parser = new PEMParser(reader)) {
                X509CertificateHolder holder = (X509CertificateHolder) parser.readObject();
                return new JcaX509CertificateConverter().getCertificate(holder);
            } catch (CertificateException e) {
                throw new IOException(e);
            }
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        KeyGenerator generator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
        generator.init(256);
        SecretKey secretKey = generator.generateKey();
        SecretKeySpec spec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
        SecretKey secretKey1 = factory.generateSecret(spec);
    }

}
