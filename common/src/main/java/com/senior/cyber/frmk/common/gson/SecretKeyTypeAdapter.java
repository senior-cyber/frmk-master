package com.senior.cyber.frmk.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Security;
import java.util.Base64;

public class SecretKeyTypeAdapter extends TypeAdapter<SecretKey> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Override
    public void write(JsonWriter out, SecretKey value) throws IOException {
        if (value != null) {
            out.value(value.getFormat() + ">" + Base64.getEncoder().withoutPadding().encodeToString(value.getEncoded()));
        } else {
            out.nullValue();
        }
    }

    @Override
    public SecretKey read(JsonReader in) throws IOException {
        String value = in.nextString();
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

}
