package com.senior.cyber.frmk.transport;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HandlerDate extends Handler {

    public HandlerDate() {
        super(Transport.TYPE_DATE);
    }

    public byte[] serialize(Date value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream stream = new DataOutputStream(bytes)) {
            stream.writeByte(getDataType());
            if (value == null) {
                stream.writeBoolean(false);
            } else {
                stream.writeBoolean(true);

                ByteArrayOutputStream dataBytes = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(dataBytes);

                dataStream.writeLong(value.getTime());

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(Date[] values) throws IOException {
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
                for (Date value : values) {
                    dataStream.writeLong(value.getTime());
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public byte[] serialize(List<Date> values) throws IOException {
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
                for (Date value : values) {
                    if (value != null) {
                        size++;
                    }
                }
                dataStream.writeInt(size);
                for (Date value : values) {
                    if (value != null) {
                        dataStream.writeLong(value.getTime());
                    }
                }

                byte[] dataTemp = dataBytes.toByteArray();
                stream.writeInt(dataTemp.length);
                stream.write(dataTemp);
            }
        }
        return bytes.toByteArray();
    }

    public Date deserialize(byte[] serializeData) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public List<Date> deserializeList(byte[] serializeData) throws IOException {
        return deserializeList(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    public Date[] deserializeArray(byte[] serializeData) throws IOException {
        return deserializeArray(new DataInputStream(new ByteArrayInputStream(serializeData)));
    }

    protected Date deserialize(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType()) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                return new Date(stream.readLong());
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(dataType + " is not support");
        }
    }

    protected Date[] deserializeArray(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                Date[] values = new Date[size];
                for (int i = 0; i < size; i++) {
                    Date value = new Date(stream.readLong());
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

    protected List<Date> deserializeList(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        if (dataType == getDataType() + 1) {
            boolean notNull = stream.readBoolean();
            if (notNull) {
                int dataSize = stream.readInt();
                int size = stream.readInt();
                List<Date> values = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Date value = new Date(stream.readLong());
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
