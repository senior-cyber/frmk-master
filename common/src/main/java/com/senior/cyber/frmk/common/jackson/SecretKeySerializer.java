package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.StringWriter;
import java.security.Security;
import java.util.Base64;

public class SecretKeySerializer extends StdSerializer<SecretKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public SecretKeySerializer(Class<SecretKey> t) {
        super(t);
    }

    public SecretKeySerializer(JavaType type) {
        super(type);
    }

    public SecretKeySerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public SecretKeySerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(SecretKey value, JsonGenerator json, SerializerProvider provider) throws IOException {
        if (value != null) {
            json.writeString(value.getFormat() + ">" + Base64.getEncoder().withoutPadding().encodeToString(value.getEncoded()));
        } else {
            json.writeNull();
        }
    }

}
