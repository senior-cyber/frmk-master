package com.senior.cyber.frmk.common.function;

import java.nio.ByteBuffer;

public class Bytes {

    private static byte SHORT_LENGTH = 2;
    private static ByteBuffer SHORT = ByteBuffer.allocateDirect(SHORT_LENGTH);

    private static byte INTEGER_LENGTH = 4;
    private static ByteBuffer INTEGER = ByteBuffer.allocateDirect(INTEGER_LENGTH);

    private static byte LONG_LENGTH = 8;
    private static ByteBuffer LONG = ByteBuffer.allocateDirect(LONG_LENGTH);

    private static byte DOUBLE_LENGTH = 8;
    private static ByteBuffer DOUBLE = ByteBuffer.allocateDirect(DOUBLE_LENGTH);

    private static byte FLOAT_LENGTH = 4;
    private static ByteBuffer FLOAT = ByteBuffer.allocateDirect(FLOAT_LENGTH);

    private static byte CHARACTER_LENGTH = 2;
    private static ByteBuffer CHARACTER = ByteBuffer.allocateDirect(CHARACTER_LENGTH);

    public static synchronized char toCharacter(byte[] bytes) {
        return toCharacter(bytes[0], bytes[1]);
    }

    public static synchronized byte[] toBytes(char value) {
        CHARACTER.rewind();
        CHARACTER.putChar(value);
        CHARACTER.rewind();
        byte[] bytes = new byte[CHARACTER_LENGTH];
        CHARACTER.get(bytes);
        return bytes;
    }

    public static synchronized char toCharacter(byte b1, byte b2) {
        CHARACTER.rewind();
        CHARACTER.put(b1);
        CHARACTER.put(b2);
        CHARACTER.rewind();
        return CHARACTER.getChar();
    }

    public static synchronized float toFloat(byte[] bytes) {
        return toFloat(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    public static synchronized byte[] toBytes(float value) {
        FLOAT.rewind();
        FLOAT.putFloat(value);
        FLOAT.rewind();
        byte[] bytes = new byte[FLOAT_LENGTH];
        FLOAT.get(bytes);
        return bytes;
    }

    public static synchronized float toFloat(byte b1, byte b2, byte b3, byte b4) {
        FLOAT.rewind();
        FLOAT.put(b1);
        FLOAT.put(b2);
        FLOAT.put(b3);
        FLOAT.put(b4);
        FLOAT.rewind();
        return FLOAT.getFloat();
    }

    public static synchronized double toDouble(byte[] bytes) {
        return toDouble(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public static synchronized byte[] toBytes(double value) {
        DOUBLE.rewind();
        DOUBLE.putDouble(value);
        DOUBLE.rewind();
        byte[] bytes = new byte[DOUBLE_LENGTH];
        DOUBLE.get(bytes);
        return bytes;
    }

    public static synchronized double toDouble(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        DOUBLE.rewind();
        DOUBLE.put(b1);
        DOUBLE.put(b2);
        DOUBLE.put(b3);
        DOUBLE.put(b4);
        DOUBLE.put(b5);
        DOUBLE.put(b6);
        DOUBLE.put(b7);
        DOUBLE.put(b8);
        DOUBLE.rewind();
        return DOUBLE.getDouble();
    }

    public static synchronized long toLong(byte[] bytes) {
        return toLong(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public static synchronized byte[] toBytes(long value) {
        LONG.rewind();
        LONG.putLong(value);
        LONG.rewind();
        byte[] bytes = new byte[LONG_LENGTH];
        LONG.get(bytes);
        return bytes;
    }

    public static synchronized long toLong(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        LONG.rewind();
        LONG.put(b1);
        LONG.put(b2);
        LONG.put(b3);
        LONG.put(b4);
        LONG.put(b5);
        LONG.put(b6);
        LONG.put(b7);
        LONG.put(b8);
        LONG.rewind();
        return LONG.getLong();
    }

    public static synchronized short toShort(byte[] bytes) {
        return toShort(bytes[0], bytes[1]);
    }

    public static synchronized byte[] toBytes(short value) {
        SHORT.rewind();
        SHORT.putShort(value);
        SHORT.rewind();
        byte[] bytes = new byte[SHORT_LENGTH];
        SHORT.get(bytes);
        return bytes;
    }

    public static synchronized short toShort(byte b1, byte b2) {
        SHORT.rewind();
        SHORT.put(b1);
        SHORT.put(b2);
        SHORT.rewind();
        return SHORT.getShort();
    }

    public static synchronized int toInteger(byte[] bytes) {
        return toInteger(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    public static synchronized byte[] toBytes(int value) {
        INTEGER.rewind();
        INTEGER.putInt(value);
        INTEGER.rewind();
        byte[] bytes = new byte[INTEGER_LENGTH];
        INTEGER.get(bytes);
        return bytes;
    }

    public static synchronized int toInteger(byte b1, byte b2, byte b3, byte b4) {
        INTEGER.rewind();
        INTEGER.put(b1);
        INTEGER.put(b2);
        INTEGER.put(b3);
        INTEGER.put(b4);
        INTEGER.rewind();
        return INTEGER.getInt();
    }

    public static void transfer(byte[] from, byte[] to) {
        System.arraycopy(from, 0, to, 0, from.length);
    }

    public static byte[] extract(byte[] values, int offset, int length) {
        byte[] extract = new byte[length];
        for (int i = 0; i < length; i++) {
            extract[i] = values[i + offset];
        }
        return extract;
    }

    public static byte[] join(Object... values) {
        if (values == null || values.length == 0) {
            return new byte[0];
        }
        int size = 0;
        for (Object value : values) {
            if (value != null) {
                if (value.getClass() == Byte.class || value.getClass() == byte.class) {
                    size++;
                } else if (value.getClass() == Integer.class || value.getClass() == int.class) {
                    size++;
                } else if (value.getClass() == byte[].class) {
                    byte[] v = (byte[]) value;
                    size = size + v.length;
                } else if (value.getClass() == Byte[].class) {
                    Byte[] v = (Byte[]) value;
                    for (Byte p : v) {
                        if (p != null) {
                            size++;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("invalid " + value.getClass().getName());
                }
            }
        }
        byte[] result = new byte[size];
        size = 0;
        for (Object value : values) {
            if (value != null) {
                if (value.getClass() == Byte.class || value.getClass() == byte.class) {
                    result[size] = (byte) value;
                    size++;
                } else if (value.getClass() == Integer.class || value.getClass() == int.class) {
                    result[size] = (byte) (int) value;
                    size++;
                } else if (value.getClass() == byte[].class) {
                    byte[] v = (byte[]) value;
                    for (byte p : v) {
                        result[size] = p;
                        size++;
                    }
                } else if (value.getClass() == Byte[].class) {
                    Byte[] v = (Byte[]) value;
                    for (Byte p : v) {
                        if (p != null) {
                            result[size] = (byte) p;
                            size++;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("invalid " + value.getClass().getName());
                }
            }
        }
        return result;
    }

}
