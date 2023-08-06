package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Security;
import java.security.cert.X509Certificate;

public class CertificateSerializer extends StdSerializer<X509Certificate> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public CertificateSerializer(Class<X509Certificate> t) {
        super(t);
    }

    public CertificateSerializer(JavaType type) {
        super(type);
    }

    public CertificateSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public CertificateSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(X509Certificate value, JsonGenerator json, SerializerProvider provider) throws IOException {
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
