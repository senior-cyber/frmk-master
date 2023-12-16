package com.senior.cyber.frmk.transport;

public abstract class Handler {

    private final byte dataType;

    protected Handler(byte dataType) {
        this.dataType = dataType;
    }

    public byte getDataType() {
        return dataType;
    }

}
