package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

public class MsSqlSelectQuery extends SelectQuery {

    public MsSqlSelectQuery(String tableName) {
        super(tableName);
    }

    public MsSqlSelectQuery(String tableName, boolean forUpdate) {
        super(tableName, forUpdate);
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (this.field.isEmpty()) {
            this.field.add("*");
        }
        builder.append(StringUtils.join(this.field, ", "));
        builder.append(" FROM ");
        builder.append(this.tableName);
        if (!this.join.isEmpty()) {
            builder.append(" ").append(StringUtils.join(this.join, " "));
        }
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        if (!this.groupBy.isEmpty()) {
            builder.append(" GROUP BY ").append(StringUtils.join(this.groupBy, ", "));
        }
        if (!this.having.isEmpty()) {
            builder.append(" HAVING ").append(StringUtils.join(this.having, " AND "));
        }
        if (!this.orderBy.isEmpty()) {
            builder.append(" ORDER BY ").append(StringUtils.join(this.orderBy, ", "));
        }
        if (this.pagination) {
            builder.append(" OFFSET ").append(this.offset).append(" ROWS FETCH NEXT ").append(this.number).append(" ROWS ONLY ");
        }
        if (this.forUpdate) {
            builder.append(" FOR UPDATE");
        }
        this.cached = builder.toString();
        this.dirty = false;
        return this.cached;
    }

}
