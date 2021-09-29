package com.senior.cyber.frmk.common.model;

public enum AccentColor {

    Blue("accent-primary"),
    Yellow("accent-warning"),
    Cyan("accent-info"),
    Red("accent-red"),
    Green("accent-success"),
    Indigo("accent-indigo"),
    Navy("accent-navy"),
    Purple("accent-purple"),
    Fuchsia("accent-fuchsia"),
    Pink("accent-pink"),
    Maroon("accent-maroon"),
    Orange("accent-orange"),
    Lime("accent-lime"),
    Teal("accent-teal"),
    Olive("accent-olive");

    private String style;

    AccentColor(String style) {
        this.style = style;
    }

    public String getStyle() {
        return this.style;
    }

}
