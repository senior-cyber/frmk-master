package com.senior.cyber.frmk.common.jwt;

public enum Algorithm {

    HS256(256), HS384(384), HS512(512),
    RS256(256), RS384(384), RS512(512),
    ES256(256), ES384(384), ES512(512),
    PS256(256), PS384(384);

    private final int size;

    Algorithm(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}