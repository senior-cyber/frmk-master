package com.senior.cyber.frmk.jdbc.query;

import java.io.Serializable;

public class Param implements Serializable {

    private final String name;

    private final Object value;

    public Param(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
