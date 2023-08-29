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

public class CsrDeserializer extends StdDeserializer<PKCS10CertificationRequest> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CsrDeserializer() {
        super(PKCS10CertificationRequest.class);
    }

    @Override
    public PKCS10CertificationRequest deserialize(JsonParser json, DeserializationContext context) throws IOException {
        String pem = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(pem)) {
            return convert(pem);
        }
        return null;
    }

    public static PKCS10CertificationRequest convert(String value) throws IOException {
        try (PEMParser parser = new PEMParser(new StringReader(value))) {
            Object objectHolder = parser.readObject();
            if (objectHolder instanceof PKCS10CertificationRequest holder) {
                return holder;
            } else {
                throw new java.lang.UnsupportedOperationException(objectHolder.getClass().getName());
            }
        }
    }

}
