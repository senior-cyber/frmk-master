package com.senior.cyber.frmk.common.model;

public class Caption {

    private final String text;

    private final String icon;

    public Caption(String text, String icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

}
