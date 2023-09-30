package com.senior.cyber.frmk.jdbc.query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class SelectQuery extends WhereQuery {

    private static final long serialVersionUID = 1L;

    protected final String tableName;

    protected final List<String> field = new LinkedList<>();
    protected final List<String> join = new LinkedList<>();
    protected final List<String> orderBy = new LinkedList<>();
    protected final List<String> having = new LinkedList<>();
    protected final List<String> groupBy = new LinkedList<>();
    protected final boolean forUpdate;
    protected boolean pagination = false;
    protected long offset;
    protected long number;

    public SelectQuery(String tableName) {
        this(tableName, false);
    }

    public SelectQuery(String tableName, boolean forUpdate) {
        this.tableName = tableName;
        this.forUpdate = forUpdate;
    }

    public Long getOffset() {
        return this.offset;
    }

    public SelectQuery setOffset(Long offset) {
        this.offset = offset;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public Long getNumber() {
        return this.number;
    }

    public SelectQuery setNumber(long number) {
        this.number = number;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public SelectQuery addField(String field) {
        this.field.add(field);
        this.dirty = true;
        return this;
    }

    public SelectQuery addField(String... fields) {
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                addField(field);
            }
            this.dirty = true;
        }
        return this;
    }

    public void addHaving(String criteria) {
        this.having.add(criteria);
        this.dirty = true;
    }

    public void addHaving(String criteria, Map<String, Object> params) {
        this.having.add(criteria);
        if (params != null) {
            this.param.putAll(params);
        }
        this.dirty = true;
    }

    public SelectQuery addOrderBy(String orderBy) {
        this.orderBy.add(orderBy);
        this.dirty = true;
        return this;
    }

    public SelectQuery addOrderBy(String field, String sort) {
        this.orderBy.add(field + " " + sort);
        this.dirty = true;
        return this;
    }

    public SelectQuery addGroupBy(String field) {
        this.groupBy.add(field);
        this.dirty = true;
        return this;
    }

    public SelectQuery addJoin(String join) {
        this.join.add(join);
        return this;
    }

    public SelectQuery setLimit(Long offset, Long number) {
        this.offset = offset;
        this.number = number;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public abstract String toSQL();

}