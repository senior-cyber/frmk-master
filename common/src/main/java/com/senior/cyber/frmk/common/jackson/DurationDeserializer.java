package com.senior.cyber.frmk.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.joda.time.Duration;

import java.io.IOException;
import java.security.Security;

public class DurationDeserializer extends StdDeserializer<Duration> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public DurationDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser json, DeserializationContext context) throws IOException {
        String v = json.readValueAs(String.class);
        if (!StringUtils.isEmpty(v)) {
            return Duration.parse(v);
        }
        return null;
    }

}
