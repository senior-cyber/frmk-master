package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Security;

public class CertificationRequestSerializer extends StdSerializer<PKCS10CertificationRequest> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CertificationRequestSerializer(Class<PKCS10CertificationRequest> t) {
        super(t);
    }

    public CertificationRequestSerializer(JavaType type) {
        super(type);
    }

    public CertificationRequestSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public CertificationRequestSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(PKCS10CertificationRequest value, JsonGenerator json, SerializerProvider provider) throws IOException {
        if (value == null) {
            json.writeNull();
        } else {
            StringWriter pem = new StringWriter();
            try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
                writer.writeObject(value);
            }
            json.writeString(pem.toString());
        }
    }

}
