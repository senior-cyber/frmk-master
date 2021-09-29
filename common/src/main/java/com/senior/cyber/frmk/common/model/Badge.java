package com.senior.cyber.frmk.common.model;

import java.io.Serializable;

public class Badge implements Serializable {

    private final String text;

    private final String type;

    public Badge(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

}
