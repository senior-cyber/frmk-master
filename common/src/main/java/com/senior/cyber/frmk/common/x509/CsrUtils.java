package com.senior.cyber.frmk.common.x509;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.Security;

public class CsrUtils {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static PKCS10CertificationRequest read(String pem) throws IOException {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object objectHolder = parser.readObject();
            if (objectHolder instanceof PKCS10CertificationRequest) {
                return (PKCS10CertificationRequest) objectHolder;
            } else {
                throw new java.lang.UnsupportedOperationException(objectHolder.getClass().getName());
            }
        }
    }

    public static PKCS10CertificationRequest read(File pem) throws IOException {
        return read(FileUtils.readFileToString(pem, StandardCharsets.UTF_8));
    }

    public static String write(PKCS10CertificationRequest csr) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(csr);
        }
        return pem.toString();
    }

}
