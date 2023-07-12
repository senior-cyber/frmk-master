package com.senior.cyber.frmk.jdbc.query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ParamQuery implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7292387740893956434L;

    protected boolean dirty = true;

    protected String cached = "";

    protected final Map<String, Object> param = new LinkedHashMap<>();

    public abstract String toSQL();

    public Map<String, Object> toParam() {
        return this.param;
    }

}