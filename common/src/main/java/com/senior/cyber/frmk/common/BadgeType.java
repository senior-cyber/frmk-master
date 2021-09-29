package com.senior.cyber.frmk.common;

public enum BadgeType {

    Default("label-default"),

    Primary("label-primary"),

    Info("label-info"),

    Warning("label-warning"),

    Danger("label-danger"),

    Success("label-success");

    private String literal;

    BadgeType(String literal) {
        this.literal = literal;
    }

    public final String getLiteral() {
        return literal;
    }

}
