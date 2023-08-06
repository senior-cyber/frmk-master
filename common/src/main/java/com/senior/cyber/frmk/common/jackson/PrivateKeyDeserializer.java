package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PrivateKeyDeserializer extends StdDeserializer<PrivateKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PrivateKeyDeserializer(Class<?> vc) {
        super(vc);
    }

    public PrivateKeyDeserializer(JavaType valueType) {
        super(valueType);
    }

    public PrivateKeyDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public PrivateKey deserialize(JsonParser json, DeserializationContext context) throws IOException, JacksonException {
        String pem = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(pem)) {
            try (StringReader reader = new StringReader(pem)) {
                try (PEMParser parser = new PEMParser(reader)) {
                    Object objectHolder = parser.readObject();
                    if (objectHolder instanceof PEMKeyPair) {
                        PEMKeyPair holder = (PEMKeyPair) objectHolder;
                        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                        converter.setProvider(BouncyCastleProvider.PROVIDER_NAME);
                        return converter.getPrivateKey(holder.getPrivateKeyInfo());
                    } else if (objectHolder instanceof PrivateKeyInfo) {
                        PrivateKeyInfo holder = (PrivateKeyInfo) objectHolder;
                        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                        converter.setProvider(BouncyCastleProvider.PROVIDER_NAME);
                        return converter.getPrivateKey(holder);
                    } else {
                        throw new IllegalArgumentException(pem + " is not readable");
                    }
                }
            }
        }
        return null;
    }
}
