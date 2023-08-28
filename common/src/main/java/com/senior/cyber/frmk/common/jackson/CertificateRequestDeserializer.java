package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.IOException;
import java.io.StringReader;
import java.security.Security;

public class CertificateRequestDeserializer extends StdDeserializer<PKCS10CertificationRequest> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CertificateRequestDeserializer() {
        super(PKCS10CertificationRequest.class);
    }

    @Override
    public PKCS10CertificationRequest deserialize(JsonParser json, DeserializationContext context) throws IOException {
        String pem = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(pem)) {
            StringReader reader = new StringReader(pem);
            try (PEMParser pemParser = new PEMParser(reader)) {
                Object o = pemParser.readObject();
                return (PKCS10CertificationRequest) o;
            }
        }
        return null;
    }

}
