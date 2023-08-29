package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.PublicKey;
import java.security.Security;

public class PublicKeySerializer extends StdSerializer<PublicKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PublicKeySerializer() {
        super(PublicKey.class);
    }

    @Override
    public void serialize(PublicKey value, JsonGenerator json, SerializerProvider provider) throws IOException {
        if (value == null) {
            json.writeNull();
        } else {
            json.writeString(convert(value));
        }
    }

    public static String convert(PublicKey value) throws IOException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(value);
        }
        return pem.toString();
    }

}
