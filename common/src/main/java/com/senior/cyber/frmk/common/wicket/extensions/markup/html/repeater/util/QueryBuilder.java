package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class QueryBuilder implements Serializable {

    private static final long serialVersionUID = 1L;

    protected List<String> select = new ArrayList<>();
    protected String from;
    protected List<String> join = new ArrayList<>();
    protected List<String> where = new ArrayList<>();
    protected List<String> orderBy = new ArrayList<>();
    protected List<String> having = new ArrayList<>();
    protected List<String> groupBy = new ArrayList<>();
    protected Long offset;
    protected Long number;

    public void addSelect(String field) {
        this.select.add(field);
    }

    public void addJoin(String table) {
        this.join.add(table);
    }

    public void addWhere(String field) {
        this.where.add(field);
    }

    public void addOrderBy(String field) {
        this.orderBy.add(field);
    }

    public void addGroupBy(String field) {
        this.groupBy.add(field);
    }

    public void addHaving(String field) {
        this.having.add(field);
    }

    public void setLimit(long offset, long number) {
        this.offset = offset;
        this.number = number;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public abstract String toSQL();

    public abstract String toCountSQL(String id);

}