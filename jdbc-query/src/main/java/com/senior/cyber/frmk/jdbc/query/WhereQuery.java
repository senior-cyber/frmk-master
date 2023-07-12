package com.senior.cyber.frmk.jdbc.query;

import java.util.*;

public abstract class WhereQuery extends ParamQuery {

    /**
     *
     */
    private static final long serialVersionUID = 3458379584516491058L;

    protected final List<String> where = new LinkedList<>();

    public void addWhere(String criteria, Param... params) {
        this.where.add(criteria);
        if (params != null) {
            for (Param param : params) {
                this.param.put(param.getName(), param.getValue());
            }
        }
        this.dirty = true;
    }

}