package com.senior.cyber.frmk.common.provider;

import org.apache.wicket.model.IModel;

import javax.persistence.Tuple;

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
