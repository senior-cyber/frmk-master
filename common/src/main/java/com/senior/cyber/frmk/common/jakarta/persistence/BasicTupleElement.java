package com.senior.cyber.frmk.common.jakarta.persistence;

import jakarta.persistence.TupleElement;

import java.io.Serializable;

public class BasicTupleElement<X> implements TupleElement<X>, Serializable {

    private String alias;

    private Class<? extends X> type;

    public BasicTupleElement(String alias, Class<? extends X> type) {
        this.type = type;
        this.alias = alias;
    }

    @Override
    public Class<? extends X> getJavaType() {
        return this.type;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

}