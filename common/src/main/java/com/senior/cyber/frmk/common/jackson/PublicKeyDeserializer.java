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
            return convert(pem);
        }
        return null;
    }

    public static PublicKey convert(String value) throws IOException {
        try (PEMParser parser = new PEMParser(new StringReader(value))) {
            Object object = parser.readObject();
            if (object instanceof X509CertificateHolder holder) {
                JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
                converter.setProvider(BouncyCastleProvider.PROVIDER_NAME);
                X509Certificate certificate = converter.getCertificate(holder);
                return certificate.getPublicKey();
            } else if (object instanceof SubjectPublicKeyInfo holder) {
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                converter.setProvider(BouncyCastleProvider.PROVIDER_NAME);
                return converter.getPublicKey(holder);
            } else {
                throw new java.lang.UnsupportedOperationException(object.getClass().getName());
            }
        } catch (CertificateException e) {
            throw new IOException(e);
        }
    }

}
