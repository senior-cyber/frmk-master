package com.senior.cyber.frmk.common.wicket.model.util;

import org.apache.wicket.model.IModel;

import jakarta.persistence.Tuple;

public class TupleModel implements IModel<Tuple> {

    protected Tuple object;

    public TupleModel(Tuple object) {
        this.object = object;
    }

    @Override
    public Tuple getObject() {
        return this.object;
    }

}
