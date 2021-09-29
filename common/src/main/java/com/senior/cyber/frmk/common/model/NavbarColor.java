package com.senior.cyber.frmk.common.model;

public enum NavbarColor {
    Blue("navbar-primary"),
    Cyan("navbar-cyan"),
    Green("navbar-success"),
    Red("navbar-danger"),
    Indigo("navbar-indigo"),
    Purple("navbar-purple"),
    Pink("navbar-pink"),
    Teal("navbar-teal"),
    Dark("navbar-gray-dark"),
    Gray("navbar-gray"),
    Yellow("navbar-warning"),
    White("navbar-white"),
    Orange("navbar-orange");

    private String style;

    NavbarColor(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
