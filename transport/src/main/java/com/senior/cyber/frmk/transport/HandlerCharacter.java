package com.senior.cyber.frmk.transport;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HandlerCharacter extends CharsetHandler {

    private static final boolean USE_WRITE_CHAR = true;

    public HandlerCharacter() {
        super(StandardCharsets.UTF_8, Transport.TYPE_CHARACTER);
    }

    public byte[] serialize(Character value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                if (USE_WRITE_CHAR) {
                    dataStream.writeChar(value);
                } else {
                    byte[] data = value.toString().getBytes(getCharset());
                    byte charset = getCharsetAsByte();
                    dataStream.writeByte(charset);
                    dataStream.writeInt(data.length);
                    dataStream.write(data);
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(char value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            stream.writeBoolean(true);

            ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(dataBytes);

            if (USE_WRITE_CHAR) {
                dataStream.writeChar(value);
            } else {
                byte[] data = String.valueOf(value).getBytes(getCharset());
                byte charset = getCharsetAsByte();
                dataStream.writeByte(charset);
                dataStream.writeInt(data.length);
                dataStream.write(data);
            }

            byte[] dataTemp = dataBytes.toByteArray();
            stream.writeInt(dataTemp.length);
            stream.write(dataTemp);
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(char[] values) throws IOException {
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
                if (USE_WRITE_CHAR) {
                    for (char value : values) {
                        dataStream.writeChar(value);
                    }
                } else {
                    byte charset = getCharsetAsByte();
                    dataStream.writeByte(charset);
                    for (char value : values) {
                        byte[] data = String.valueOf(value).getBytes(getCharset());
                        dataStream.writeInt(data.length);
                        dataStream.write(data);
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<Character> values) throws IOException {
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
                for (Character value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                if (USE_WRITE_CHAR) {
                    for (Character value : values) {
                        if (value != null) {
                            dataStream.writeChar(value);
                        }
                    }
                } else {
                    byte charset = getCharsetAsByte();
                    dataStream.writeByte(charset);
                    for (Character value : values) {
                        if (value != null) {
                            byte[] data = String.valueOf(value).getBytes(getCharset());
                            dataStream.writeInt(data.length);
                            dataStream.write(data);
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

    public Character deserialize(byte[] serializeData) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public List<Character> deserializeList(byte[] serializeData) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public char[] deserializeArray(byte[] serializeData) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    protected Character deserialize(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                if (USE_WRITE_CHAR) {
                    return stream.readChar();
                } else {
                    Charset charset = getCharset(stream.readByte());
                    int length = stream.readInt();
                    String data = new String(stream.readNBytes(length), charset);
                    return data.charAt(0);
                }
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected char[] deserializeArray(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                char[] values = new char[size];
                if (USE_WRITE_CHAR) {
                    for (int i = 0; i < size; i++) {
                        char value = stream.readChar();
                        values[i] = value;
                    }
                } else {
                    Charset charset = getCharset(stream.readByte());
                    for (int i = 0; i < size; i++) {
                        int length = stream.readInt();
                        String data = new String(stream.readNBytes(length), charset);
                        char value = data.charAt(0);
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

    protected List<Character> deserializeList(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                List<Character> values = new ArrayList<>(size);
                if (USE_WRITE_CHAR) {
                    for (int i = 0; i < size; i++) {
                        Character value = stream.readChar();
                        values.add(value);
                    }
                } else {
                    Charset charset = getCharset(stream.readByte());
                    for (int i = 0; i < size; i++) {
                        int length = stream.readInt();
                        String data = new String(stream.readNBytes(length), charset);
                        Character value = data.charAt(0);
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

}
