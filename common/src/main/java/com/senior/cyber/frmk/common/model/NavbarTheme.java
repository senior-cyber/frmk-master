package com.senior.cyber.frmk.common.model;

public enum NavbarTheme {

    Dark("navbar-dark"),
    Light("navbar-light");

    private String style;

    NavbarTheme(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
