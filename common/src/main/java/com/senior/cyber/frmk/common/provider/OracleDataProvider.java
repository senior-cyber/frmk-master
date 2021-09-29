package com.senior.cyber.frmk.common.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public abstract class OracleDataProvider extends QueryDataProvider {

    public OracleDataProvider() {
    }

    public OracleDataProvider(String from) {
        super(from);
    }

    @Override
    protected String asName(String name) {
        return "\"" + name + "\"";
    }

    protected QueryBuilder buildQuery(Map<String, Object> params, String orderBy) {
        List<String> where = buildWhere(params);
        List<String> having = buildHaving(params);
        QueryBuilder builder = new OracleQueryBuilder();
        builder.setFrom(getFrom());
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                builder.addJoin(join);
            }
        }
        if (!getFields().isEmpty()) {
            for (String field : getFields()) {
                builder.addSelect(field);
            }
        }
        if (!where.isEmpty()) {
            for (String s : where) {
                builder.addWhere(s);
            }
        }
        if (!having.isEmpty()) {
            for (String s : having) {
                builder.addHaving(s);
            }
        }
        if (this.groupBy != null && !"".equals(this.groupBy)) {
            builder.addGroupBy(this.groupBy);
        }
        if (orderBy != null && !"".equals(orderBy)) {
            builder.addOrderBy(orderBy);
        }
        return builder;
    }

    protected static class OracleQueryBuilder extends QueryBuilder {

        @Override
        public String toSQL() {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT ");
            if (this.select.isEmpty()) {
                this.select.add("*");
            }
            builder.append(StringUtils.join(this.select, ", "));
            builder.append(" FROM ");
            builder.append(this.from).append(" ");
            builder.append(StringUtils.join(this.join, " "));
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
            if (this.offset != null && this.number != null) {
                builder.append(" OFFSET " + this.offset + " ROWS FETCH NEXT " + this.number + " ROWS ONLY");
            }
            return builder.toString();
        }

        @Override
        public String toCountSQL(String id) {
            if (this.groupBy == null || this.groupBy.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append("SELECT ");
                builder.append("COUNT(" + id + ") ");
                builder.append(" FROM ");
                builder.append(this.from).append(" ");
                builder.append(StringUtils.join(this.join, " "));
                if (!this.where.isEmpty()) {
                    builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
                }
                return builder.toString();
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("SELECT ");
                builder.append("count(" + id + ") over()");
                builder.append(" FROM ");
                builder.append(this.from).append(" ");
                builder.append(StringUtils.join(this.join, " "));
                if (!this.where.isEmpty()) {
                    builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
                }
                builder.append(" GROUP BY ").append(StringUtils.join(this.groupBy, ", "));
                if (!this.having.isEmpty()) {
                    builder.append(" HAVING ").append(StringUtils.join(this.having, " AND "));
                }
                if (this.offset != null && this.number != null) {
                    builder.append(" OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY");
                }
                return builder.toString();
            }
        }

    }

}
