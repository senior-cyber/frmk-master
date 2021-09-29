package com.senior.cyber.frmk.common.pki;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateUtils {

    public static X509Certificate read(String pem) throws IOException, CertificateException {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object object = parser.readObject();
            if (object instanceof JcaX509CertificateHolder) {
                JcaX509CertificateHolder holder = (JcaX509CertificateHolder) object;
                return new JcaX509CertificateConverter().getCertificate(holder);
            } else if (object instanceof X509CertificateHolder) {
                X509CertificateHolder holder = (X509CertificateHolder) object;
                return new JcaX509CertificateConverter().getCertificate(holder);
            } else {
                throw new java.lang.UnsupportedOperationException(object.getClass().getName());
            }
        }
    }

    public static X509Certificate read(File pem) throws IOException, CertificateException {
        return read(FileUtils.readFileToString(pem, StandardCharsets.UTF_8));
    }

    public static String write(X509Certificate certificate) throws IOException, CertificateEncodingException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(certificate);
        }
        return pem.toString();
    }

}
