package com.senior.cyber.frmk.transport;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HandlerString extends CharsetHandler {

    private static final boolean USE_WRITE_UTF = false;

    public HandlerString() {
        super(StandardCharsets.UTF_8, Transport.TYPE_STRING);
    }

    public byte[] serialize(String value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                if (USE_WRITE_UTF) {
                    dataStream.writeUTF(value);
                } else {
                    byte charset = getCharsetAsByte();
                    byte[] data = value.getBytes(getCharset());
                    dataStream.writeByte(charset);
                    dataStream.writeInt(data.length);
                    for (int i = 0; i < data.length; i++) {
                        byte p = (byte) ~data[i];
                        dataStream.write(p);
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(String[] values) throws IOException {
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
                if (USE_WRITE_UTF) {
                    for (String value : values) {
                        dataStream.writeUTF(value);
                    }
                } else {
                    byte charset = getCharsetAsByte();
                    dataStream.writeByte(charset);
                    for (String value : values) {
                        byte[] data = value.getBytes(getCharset());
                        dataStream.writeInt(data.length);
                        for (int i = 0; i < data.length; i++) {
                            byte p = (byte) ~data[i];
                            dataStream.write(p);
                        }
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<String> values) throws IOException {
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
                for (String value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                if (USE_WRITE_UTF) {
                    for (String value : values) {
                        dataStream.writeUTF(value);
                    }
                } else {
                    byte charset = getCharsetAsByte();
                    dataStream.writeByte(charset);
                    for (String value : values) {
                        byte[] data = value.getBytes(getCharset());
                        dataStream.writeInt(data.length);
                        for (int i = 0; i < data.length; i++) {
                            byte p = (byte) ~data[i];
                            dataStream.write(p);
                        }
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public String deserialize(byte[] serializeData) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public List<String> deserializeList(byte[] serializeData) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public String[] deserializeArray(byte[] serializeData) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    protected String deserialize(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                if (USE_WRITE_UTF) {
                    return stream.readUTF();
                } else {
                    Charset charset = getCharset(stream.readByte());
                    int length = stream.readInt();
                    byte[] data = new byte[length];
                    stream.read(data);
                    for (int i = 0; i < data.length; i++) {
                        data[i] = (byte) ~data[i];
                    }
                    return new String(data, charset);
                }
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected List<String> deserializeList(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                List<String> values = new ArrayList<>(size);
                if (USE_WRITE_UTF) {
                    for (int i = 0; i < size; i++) {
                        String value = stream.readUTF();
                        values.add(value);
                    }
                } else {
                    Charset charset = getCharset(stream.readByte());
                    for (int i = 0; i < size; i++) {
                        int length = stream.readInt();
                        byte[] data = new byte[length];
                        stream.read(data);
                        for (int j = 0; j < data.length; j++) {
                            data[j] = (byte) ~data[j];
                        }
                        String value = new String(data, charset);
                        values.add(value);
                    }
                }
                return values;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected String[] deserializeArray(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                String[] values = new String[size];
                if (USE_WRITE_UTF) {
                    for (int i = 0; i < size; i++) {
                        String value = stream.readUTF();
                        values[i] = value;
                    }
                } else {
                    Charset charset = getCharset(stream.readByte());
                    for (int i = 0; i < size; i++) {
                        int length = stream.readInt();
                        byte[] data = new byte[length];
                        stream.read(data);
                        for (int j = 0; j < data.length; j++) {
                            data[j] = (byte) ~data[j];
                        }
                        String value = new String(data, charset);
                        values[i] = value;
                    }
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
