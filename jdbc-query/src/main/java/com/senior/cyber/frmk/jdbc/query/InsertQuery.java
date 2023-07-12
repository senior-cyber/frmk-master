package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertQuery extends ParamQuery {

    /**
     *
     */
    private static final long serialVersionUID = -8830389077520597417L;

    protected final String tableName;

    protected final Map<String, String> fields = new LinkedHashMap<>();

    public InsertQuery(String tableName) {
        this.tableName = tableName;
    }

    public void addValue(String field, String valueExpression, Param... params) {
        this.fields.put(field, valueExpression);
        if (params != null) {
            for (Param param : params) {
                this.param.put(param.getName(), param.getValue());
            }
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