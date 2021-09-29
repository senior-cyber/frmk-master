package com.senior.cyber.frmk.common.function;

public class ByteExtension {

    private static final int BYTE = 8;

    public static int rotateLeft(int byteValue, int bit) {
        return (byteValue << bit) | (byteValue >> (BYTE - bit));
    }

    public static int rotateRight(int byteValue, int bit) {
        return (byteValue >> bit) | (byteValue << (BYTE - bit));
    }
    
}
