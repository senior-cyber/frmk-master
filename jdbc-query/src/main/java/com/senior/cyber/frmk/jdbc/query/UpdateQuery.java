package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UpdateQuery extends WhereQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1950223463620867110L;

    protected final String tableName;

    protected final List<String> set = new LinkedList<>();

    public UpdateQuery(String tableName) {
        this.tableName = tableName;
    }

    public void addSet(String expression, Param... params) {
        this.set.add(expression);
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
        List<String> builder = new LinkedList<>();
        builder.add("UPDATE");
        builder.add(this.tableName);
        builder.add("SET");
        builder.add(StringUtils.join(this.set, ", "));
        if (!this.where.isEmpty()) {
            builder.add("WHERE");
            builder.add(StringUtils.join(this.where, " AND "));
        }
        this.dirty = false;
        this.cached = StringUtils.join(builder, " ");
        return this.cached;
    }
}