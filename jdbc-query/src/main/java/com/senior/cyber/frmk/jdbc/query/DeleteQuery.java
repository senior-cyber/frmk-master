package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class DeleteQuery extends WhereQuery {

    private static final long serialVersionUID = 1L;

    protected final String tableName;

    public DeleteQuery(String tableName) {
        this.tableName = tableName;
    }

    public String toSQL() {
        if (!this.dirty) {
            return cached;
        }
        List<String> builder = new LinkedList<>();
        builder.add("DELETE");
        builder.add("FROM");
        builder.add(this.tableName);
        if (!this.where.isEmpty()) {
            builder.add("WHERE");
            builder.add(StringUtils.join(this.where, " AND "));
        }
        this.cached = StringUtils.join(builder, " ");
        this.dirty = false;
        return this.cached;
    }
}