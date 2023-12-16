package com.senior.cyber.frmk.transport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HandlerFloat extends Handler {

    public HandlerFloat() {
        super(Transport.TYPE_FLOAT);
    }

    public byte[] serialize(Float value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                dataStream.writeFloat(value);

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(float value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            stream.writeBoolean(true);

            ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(dataBytes);

            dataStream.writeFloat(value);

            byte[] dataTemp = dataBytes.toByteArray();
            stream.writeInt(dataTemp.length);
            stream.write(dataTemp);
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(float[] values) throws IOException {
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
                for (float value : values) {
                    dataStream.writeFloat(value);
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<Float> values) throws IOException {
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
                for (Float value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                for (Float value : values) {
                    if (value != null) {
                        dataStream.writeFloat(value);
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public Float deserialize(byte[] serializeData) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public List<Float> deserializeList(byte[] serializeData) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public float[] deserializeArray(byte[] serializeData) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    protected Float deserialize(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                return stream.readFloat();
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected float[] deserializeArray(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                float[] values = new float[size];
                for (int i = 0; i < size; i++) {
                    float value = stream.readFloat();
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

    protected List<Float> deserializeList(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                List<Float> values = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Float value = stream.readFloat();
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
