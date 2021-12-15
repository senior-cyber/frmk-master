package com.senior.cyber.frmk.common.pki;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PublicKeyUtils {

    public static PublicKey read(String pem) throws IOException {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object object = parser.readObject();
            if (object instanceof X509CertificateHolder) {
                X509CertificateHolder holder = (X509CertificateHolder) object;
                X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(holder);
                return certificate.getPublicKey();
            } else if (object instanceof SubjectPublicKeyInfo) {
                SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) object;
                return new JcaPEMKeyConverter().getPublicKey(subjectPublicKeyInfo);
            } else {
                throw new java.lang.UnsupportedOperationException(object.getClass().getName());
            }
        } catch (CertificateException e) {
            throw new IOException(e);
        }
    }

    public static PublicKey read(File pem) throws IOException {
        return read(FileUtils.readFileToString(pem, StandardCharsets.UTF_8));
    }

    public static String write(PublicKey publicKey) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(publicKey);
        }
        return pem.toString();
    }

}
