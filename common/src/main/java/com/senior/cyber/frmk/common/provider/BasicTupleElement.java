package com.senior.cyber.frmk.common.provider;

import javax.persistence.TupleElement;
import java.io.Serializable;

public class BasicTupleElement<X> implements TupleElement<X>, Serializable {

    private Class<? extends X> type;

    private String alias;

    public BasicTupleElement(Class<? extends X> type, String alias) {
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