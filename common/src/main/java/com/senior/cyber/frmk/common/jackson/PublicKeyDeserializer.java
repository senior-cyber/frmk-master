package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PublicKeyDeserializer extends StdDeserializer<PublicKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PublicKeyDeserializer() {
        super(PublicKey.class);
    }

    @Override
    public PublicKey deserialize(JsonParser json, DeserializationContext context) throws IOException {
        String pem = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(pem)) {
            try (StringReader reader = new StringReader(pem)) {
                try (PEMParser parser = new PEMParser(reader)) {
                    Object object = parser.readObject();
                    if (object instanceof X509CertificateHolder) {
                        X509CertificateHolder holder = (X509CertificateHolder) object;
                        X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(holder);
                        return certificate.getPublicKey();
                    } else if (object instanceof SubjectPublicKeyInfo) {
                        SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) object;
                        return new JcaPEMKeyConverter().getPublicKey(subjectPublicKeyInfo);
                    } else {
                        throw new IllegalArgumentException(pem + " is not readable");
                    }
                } catch (CertificateException e) {
                    throw new IOException(e);
                }
            }
        }
        return null;
    }

}
