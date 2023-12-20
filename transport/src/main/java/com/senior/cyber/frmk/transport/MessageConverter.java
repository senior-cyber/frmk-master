package com.senior.cyber.frmk.transport;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

public class MessageConverter implements HttpMessageConverter<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConverter.class);

    private static final MediaType support = MediaType.APPLICATION_OCTET_STREAM;

    private final Transport transport;

    public MessageConverter(Transport transport) {
        this.transport = transport;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (mediaType == null) {
            return Message.class.isAssignableFrom(clazz);
        } else {
            LOGGER.info("canRead [{}]", mediaType);
            return Message.class.isAssignableFrom(clazz) && mediaType.equals(support);
        }
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (mediaType == null) {
            return Message.class.isAssignableFrom(clazz);
        } else {
            LOGGER.info("canWrite [{}]", mediaType);
            return Message.class.isAssignableFrom(clazz) && mediaType.equals(support);
        }
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(support);
    }

    @Override
    public Message read(Class<? extends Message> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        LOGGER.info("Read [{}]", clazz.getName());
        byte[] data = IOUtils.toByteArray(inputMessage.getBody());
        return this.transport.messageHandler().deserialize(data, clazz);
    }

    @Override
    public void write(Message message, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        byte[] data = this.transport.messageHandler().serialize(message);
        outputMessage.getBody().write(data);
    }

}
