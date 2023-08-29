package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Security;

public class CsrSerializer extends StdSerializer<PKCS10CertificationRequest> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CsrSerializer() {
        super(PKCS10CertificationRequest.class);
    }

    @Override
    public void serialize(PKCS10CertificationRequest value, JsonGenerator json, SerializerProvider provider) throws IOException {
        if (value == null) {
            json.writeNull();
        } else {
            json.writeString(convert(value));
        }
    }

    public static String convert(PKCS10CertificationRequest value) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(value);
        }
        return pem.toString();
    }

}
