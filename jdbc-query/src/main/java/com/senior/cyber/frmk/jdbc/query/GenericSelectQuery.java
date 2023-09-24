package com.senior.cyber.frmk.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GenericSelectQuery extends SelectQuery {

    public GenericSelectQuery(String tableName) {
        super(tableName);
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        List<String> segment = new ArrayList<>();
        segment.add("SELECT");
        if (this.field.isEmpty()) {
            this.field.add("*");
        }
        segment.add(StringUtils.join(this.field, ", "));
        segment.add("FROM");
        segment.add(this.tableName);
        if (!this.join.isEmpty()) {
            segment.add(StringUtils.join(this.join, " "));
        }
        if (!this.where.isEmpty()) {
            segment.add("WHERE");
            segment.add(StringUtils.join(this.where, " AND "));
        }
        if (!this.groupBy.isEmpty()) {
            segment.add("GROUP BY");
            segment.add(StringUtils.join(this.groupBy, ", "));
        }
        if (!this.having.isEmpty()) {
            segment.add("HAVING");
            segment.add(StringUtils.join(this.having, " AND "));
        }
        if (!this.orderBy.isEmpty()) {
            segment.add("ORDER BY");
            segment.add(StringUtils.join(this.orderBy, ", "));
        }
        if (this.pagination) {
            segment.add("LIMIT");
            segment.add(this.offset + "," + this.number);
        }
        this.cached = StringUtils.join(segment, " ");
        this.dirty = false;
        return this.cached;
    }

}
