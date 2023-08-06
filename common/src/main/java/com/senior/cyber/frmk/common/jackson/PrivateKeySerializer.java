package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;

import java.io.IOException;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;

public class PrivateKeySerializer extends StdSerializer<PrivateKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PrivateKeySerializer(Class<PrivateKey> t) {
        super(t);
    }

    public PrivateKeySerializer(JavaType type) {
        super(type);
    }

    public PrivateKeySerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public PrivateKeySerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(PrivateKey value, JsonGenerator json, SerializerProvider provider) throws IOException {
        if (value == null) {
            json.writeNull();
        } else {
            StringWriter pem = new StringWriter();
            try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
                writer.writeObject(new JcaPKCS8Generator(value, null));
            }
            json.writeString(pem.toString());
        }
    }

}
