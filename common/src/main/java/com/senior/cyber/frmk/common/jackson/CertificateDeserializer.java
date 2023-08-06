package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;

import java.io.IOException;
import java.io.StringReader;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateDeserializer extends StdDeserializer<X509Certificate> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CertificateDeserializer(Class<?> vc) {
        super(vc);
    }

    public CertificateDeserializer(JavaType valueType) {
        super(valueType);
    }

    public CertificateDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public X509Certificate deserialize(JsonParser json, DeserializationContext context) throws IOException, JacksonException {
        String pem = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(pem)) {
            try (StringReader reader = new StringReader(pem)) {
                try (PEMParser parser = new PEMParser(reader)) {
                    X509CertificateHolder holder = (X509CertificateHolder) parser.readObject();
                    JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
                    converter.setProvider(BouncyCastleProvider.PROVIDER_NAME);
                    return converter.getCertificate(holder);
                } catch (CertificateException e) {
                    throw new IOException(e);
                }
            }
        }
        return null;
    }
}
