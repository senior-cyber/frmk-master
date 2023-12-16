package com.senior.cyber.frmk.transport;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MessageConverterFactory extends Converter.Factory {

    private Transport transport;

    public MessageConverterFactory(Transport transport) {
        this.transport = transport;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<Message> clazz = (Class<Message>) type;
        return new MessageResponseConverter(this.transport, clazz);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new MessageRequestConverter(this.transport);
    }

}
