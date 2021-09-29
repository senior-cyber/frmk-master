package com.senior.cyber.frmk.common.provider;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicTuple implements Tuple, Serializable {

    private Map<String, Object> data = new HashMap<>();

    public <X> void set(TupleElement<X> tupleElement, Object value) {
        data.put(tupleElement.getAlias(), value);
    }

    @Override
    public <X> X get(TupleElement<X> tupleElement) {
        return (X) data.get(tupleElement.getAlias());
    }

    @Override
    public <X> X get(String alias, Class<X> type) {
        return (X) this.data.get(alias);
    }

    @Override
    public Object get(String alias) {
        return this.data.get(alias);
    }

    @Override
    public <X> X get(int i, Class<X> type) {
        throw new UnsupportedOperationException("not support index based");
    }

    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException("not support index based");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("not support index based");
    }

    @Override
    public List<TupleElement<?>> getElements() {
        throw new UnsupportedOperationException("not support index based");
    }

}