package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class InsertQuery extends ParamQuery {

    private static final long serialVersionUID = 1L;

    protected final String tableName;

    protected final Map<String, String> fields = new LinkedHashMap<>();

    public InsertQuery(String tableName) {
        this.tableName = tableName;
    }

    public void addValue(String field, String valueExpression) {
        this.fields.put(field, valueExpression);
        this.dirty = true;
    }

    public void addValue(String field, String valueExpression, Map<String, Object> params) {
        this.fields.put(field, valueExpression);
        if (params != null) {
            this.param.putAll(params);
        }
        this.dirty = true;
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        this.cached = "INSERT INTO " + this.tableName + "(" + StringUtils.join(this.fields.keySet(), ", ") + ")" + " " + "VALUES" + "(" + StringUtils.join(this.fields.values(), ", ") + ")";
        this.dirty = false;
        return this.cached;
    }
}