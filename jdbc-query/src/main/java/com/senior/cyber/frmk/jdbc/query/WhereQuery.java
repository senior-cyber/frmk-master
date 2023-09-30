package com.senior.cyber.frmk.jdbc.query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class WhereQuery extends ParamQuery {

    private static final long serialVersionUID = 1L;

    protected final List<String> where = new LinkedList<>();

    public void addWhere(String criteria) {
        this.where.add(criteria);
        this.dirty = true;
    }

    public void addWhere(String criteria, Map<String, Object> params) {
        this.where.add(criteria);
        if (params != null) {
            this.param.putAll(params);
        }
        this.dirty = true;
    }

}