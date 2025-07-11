package com.senior.cyber.frmk.transport;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import java.io.IOException;

public class MessageRequestConverter implements Converter<Message, RequestBody> {

    private Transport transport;

    public MessageRequestConverter(Transport transport) {
        this.transport = transport;
    }

    @Override
    public RequestBody convert(Message value) throws IOException {
        byte[] data = transport.messageHandler().serialize(value);
        return RequestBody.create(data, MediaType.parse("application/octet-stream"));
    }

}
