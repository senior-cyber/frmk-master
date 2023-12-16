package com.senior.cyber.frmk.transport;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class CharsetHandler extends Handler {

    private final Charset charset;

    protected CharsetHandler(Charset charset, byte dataType) {
        super(dataType);
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    public Charset getCharset(byte charset) {
        if (charset == 1) {
            return StandardCharsets.UTF_8;
        } else if (charset == 2) {
            return StandardCharsets.UTF_16;
        } else if (charset == 3) {
            return StandardCharsets.UTF_16BE;
        } else if (charset == 4) {
            return StandardCharsets.UTF_16LE;
        } else if (charset == 5) {
            return StandardCharsets.US_ASCII;
        } else if (charset == 6) {
            return StandardCharsets.ISO_8859_1;
        } else {
            return null;
        }
    }

    public byte getCharsetAsByte() {
        if (getCharset() == StandardCharsets.UTF_8) {
            return 1;
        } else if (getCharset() == StandardCharsets.UTF_16) {
            return 2;
        } else if (getCharset() == StandardCharsets.UTF_16BE) {
            return 3;
        } else if (getCharset() == StandardCharsets.UTF_16LE) {
            return 4;
        } else if (getCharset() == StandardCharsets.US_ASCII) {
            return 5;
        } else if (getCharset() == StandardCharsets.ISO_8859_1) {
            return 6;
        } else {
            return 0;
        }
    }

}
