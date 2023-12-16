package com.senior.cyber.frmk.transport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HandlerDouble extends Handler {

    public HandlerDouble() {
        super(Transport.TYPE_DOUBLE);
    }

    public byte[] serialize(Double value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                dataStream.writeDouble(value);

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(double value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            stream.writeBoolean(true);

            ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(dataBytes);

            dataStream.writeDouble(value);

            byte[] dataTemp = dataBytes.toByteArray();
            stream.writeInt(dataTemp.length);
            stream.write(dataTemp);
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(double[] values) throws IOException {
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
                for (double value : values) {
                    dataStream.writeDouble(value);
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<Double> values) throws IOException {
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
                for (Double value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                for (Double value : values) {
                    if (value != null) {
                        dataStream.writeDouble(value);
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public Double deserialize(byte[] serializeData) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public List<Double> deserializeList(byte[] serializeData) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public double[] deserializeArray(byte[] serializeData) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    protected Double deserialize(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                return stream.readDouble();
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected double[] deserializeArray(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                double[] values = new double[size];
                for (int i = 0; i < size; i++) {
                    double value = stream.readDouble();
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

    protected List<Double> deserializeList(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                List<Double> values = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Double value = stream.readDouble();
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
