package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Security;
import java.util.Base64;

public class SecretKeyDeserializer extends StdDeserializer<SecretKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public SecretKeyDeserializer() {
        super(SecretKey.class);
    }

    @Override
    public SecretKey deserialize(JsonParser json, DeserializationContext context) throws IOException {
        String value = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(value)) {
            int i = value.indexOf(">");
            String f = value.substring(0, i);
            String v = value.substring(i + 1);
            try {
                SecretKeyFactory factory = SecretKeyFactory.getInstance(f, BouncyCastleProvider.PROVIDER_NAME);
                SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(v), f);
                return factory.generateSecret(spec);
            } catch (Throwable e) {
                throw new IOException(e);
            }
        }
        return null;
    }

}
