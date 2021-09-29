package com.senior.cyber.frmk.common.model;

public class Emoji {

    private final String type;

    private final String icon;

    public Emoji(String type, String icon) {
        this.type = type;
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

}
