package com.senior.cyber.frmk.common.wicket.layout;

public enum DialogSizeEnum {
    Small("modal-dialog modal-sm"), Medium("modal-dialog"), Large("modal-dialog modal-lg"), ExtraLarge("modal-dialog modal-xl");

    private final String literal;

    DialogSizeEnum(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

}