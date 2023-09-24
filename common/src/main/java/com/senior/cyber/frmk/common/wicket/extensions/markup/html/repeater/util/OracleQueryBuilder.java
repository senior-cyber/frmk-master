package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OracleQueryBuilder extends QueryBuilder {

    @Override
    public String toSQL() {
        List<String> segment = new ArrayList<>();
        segment.add("SELECT");
        if (this.select.isEmpty()) {
            this.select.add("*");
        }
        segment.add(StringUtils.join(this.select, ", "));
        segment.add("FROM");
        segment.add(this.from);
        segment.add(StringUtils.join(this.join, " "));
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
        if (this.offset != null && this.number != null) {
            segment.add("OFFSET " + this.offset + " ROWS FETCH NEXT " + this.number + " ROWS ONLY");
        }
        return StringUtils.join(segment, " ");
    }

    @Override
    public String toCountSQL(String id) {
        List<String> segment = new ArrayList<>();
        if (this.groupBy == null || this.groupBy.isEmpty()) {
            segment.add("SELECT");
            segment.add("COUNT(" + id + ")");
            segment.add("FROM");
            segment.add(this.from);
            segment.add(StringUtils.join(this.join, " "));
            if (!this.where.isEmpty()) {
                segment.add("WHERE");
                segment.add(StringUtils.join(this.where, " AND "));
            }
        } else {
            segment.add("SELECT");
            segment.add("COUNT(" + id + ") OVER()");
            segment.add("FROM");
            segment.add(this.from);
            segment.add(StringUtils.join(this.join, " "));
            if (!this.where.isEmpty()) {
                segment.add("WHERE");
                segment.add(StringUtils.join(this.where, " AND "));
            }
            segment.add("GROUP BY");
            segment.add(StringUtils.join(this.groupBy, ", "));
            if (!this.having.isEmpty()) {
                segment.add("HAVING");
                segment.add(StringUtils.join(this.having, " AND "));
            }
            segment.add("OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY");
        }
        return StringUtils.join(segment, " ");
    }

}