package com.senior.cyber.frmk.transport;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageResponseConverter implements Converter<ResponseBody, Message> {

    private Transport transport;

    private Class<Message> clazz;

    public MessageResponseConverter(Transport transport, Class<Message> clazz) {
        this.transport = transport;
        this.clazz = clazz;
    }

    @Override
    public Message convert(ResponseBody value) throws IOException {
        return transport.messageHandler().deserialize(new DataInputStream(new ByteArrayInputStream(value.bytes())), this.clazz);
    }

}