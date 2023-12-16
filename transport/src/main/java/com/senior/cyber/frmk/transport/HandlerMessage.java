package com.senior.cyber.frmk.transport;

import java.io.*;
import java.util.List;

public class HandlerMessage extends Handler {

    private final Transport transport;

    public HandlerMessage(Transport transport) {
        super(Transport.TYPE_MESSAGE);
        this.transport = transport;
    }

    public byte[] serialize(Message value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                value.writeTo(this.transport, dataStream);

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(Message[] values) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType() + 1);
            if (values == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                int length = values.length;
                dataStream.writeInt(length);
                for (Message value : values) {
                    value.writeTo(this.transport, dataStream);
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<? extends Message> values) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType() + 1);
            if (values == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                int size = 0;
                for (Message value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                for (Message value : values) {
                    if (value != null) {
                        value.writeTo(this.transport, dataStream);
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public <T extends Message> T deserialize(byte[] serializeData, Class<T> clazz) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)), clazz);
    }

    public <T extends Message> List<T> deserializeList(byte[] serializeData, Class<T> clazz) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)), clazz);
    }

    public <T extends Message> T[] deserializeArray(byte[] serializeData, Class<T> clazz) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)), clazz);
    }

    protected <T extends Message> T deserialize(DataInputStream stream, Class<T> clazz) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                MessageFactory<T> factory = this.transport.getFactory(clazz);
                T value = factory.one();
                value.readFrom(this.transport, stream);
                return value;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected <T extends Message> T[] deserializeArray(DataInputStream stream, Class<T> clazz) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                MessageFactory<T> factory = this.transport.getFactory(clazz);
                int length = stream.readInt();
                T[] values = factory.array(length);
                for (int i = 0; i < length; i++) {
                    T value = factory.one();
                    value.readFrom(this.transport, stream);
                    values[i] = value;
                }
                return values;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected <T extends Message> List<T> deserializeList(DataInputStream stream, Class<T> clazz) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                MessageFactory<T> factory = this.transport.getFactory(clazz);
                int size = stream.readInt();
                List<T> values = factory.list(size);
                for (int i = 0; i < size; i++) {
                    T value = factory.one();
                    value.readFrom(this.transport, stream);
                    values.add(value);
                }
                return values;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

}
