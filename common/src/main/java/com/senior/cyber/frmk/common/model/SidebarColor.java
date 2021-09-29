package com.senior.cyber.frmk.common.model;

public enum SidebarColor {
    BlueDark("sidebar-dark-primary"),
    BlueLight("sidebar-light-primary"),
    YellowDark("sidebar-dark-warning"),
    YellowLight("sidebar-light-warning"),
    CyanDark("sidebar-dark-info"),
    CyanLight("sidebar-light-info"),
    RedDark("sidebar-dark-red"),
    RedLight("sidebar-light-red"),
    GreenDark("sidebar-dark-success"),
    GreenLight("sidebar-light-success"),
    IndigoDark("sidebar-dark-indigo"),
    IndigoLight("sidebar-light-indigo"),
    NavyDark("sidebar-dark-navy"),
    NavyLight("sidebar-light-navy"),
    PurpleDark("sidebar-dark-purple"),
    PurpleLight("sidebar-light-purple"),
    FuchsiaDark("sidebar-dark-fuchsia"),
    FuchsiaLight("sidebar-light-fuchsia"),
    PinkDark("sidebar-dark-pink"),
    PinkLight("sidebar-light-pink"),
    MaroonDark("sidebar-dark-maroon"),
    MaroonLight("sidebar-light-maroon"),
    OrangeDark("sidebar-dark-orange"),
    OrangeLight("sidebar-light-orange"),
    LimeDark("sidebar-dark-lime"),
    LimeLight("sidebar-light-lime"),
    TealDark("sidebar-dark-teal"),
    TealLight("sidebar-light-teal"),
    OliveDark("sidebar-dark-olive"),
    OliveLight("sidebar-light-olive");

    private String style;

    SidebarColor(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
