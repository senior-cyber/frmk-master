package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.jdbc.query.GenericSelectQuery;
import com.senior.cyber.frmk.jdbc.query.Param;
import com.senior.cyber.frmk.jdbc.query.SelectQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SingleChoiceProvider<Id, Label> extends ISingleChoiceProvider<Id, Label> {

    /**
     *
     */
    private static final long serialVersionUID = -7876227423469837408L;
    protected final String idField;
    protected final String queryField;
    protected final String labelField;
    private final String table;
    private final Map<String, String> join;

    private final Map<String, String> where;
    private final String orderBy;
    private boolean disabled = false;
    private String groupBy;

    public SingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            String table, String idField) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, idField);
    }

    public SingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            String table, String idField, String queryField) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, queryField, queryField);
    }

    public SingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            String table, String idField, String queryField, String orderBy) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, queryField, orderBy, queryField);
    }

    public SingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            String table, String idField, String queryField, String orderBy,
            String labelField) {
        super(idType, idConvertor, labelType, labelConvertor);
        this.table = table;
        this.idField = idField;
        this.orderBy = orderBy;
        this.labelField = labelField;
        this.queryField = queryField;
        this.join = new HashMap<>();
        this.where = new HashMap<>();
    }

    public void applyWhere(String key, String filter) {
        this.where.put(key, filter);
    }

    public String removeWhere(String key) {
        return this.where.remove(key);
    }

    public void applyJoin(String key, String join) {
        this.join.put(key, join);
    }

    public String removeJoin(String key) {
        return this.join.remove(key);
    }

    @Override
    public Option toChoice(String s) {
        if (isDisabled()) {
            return null;
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        if (this.groupBy != null && !"".equals(this.groupBy)) {
            selectQuery.addField("MAX(" + this.idField + ") AS id");
            selectQuery.addField("MAX(" + this.labelField + ") AS text");
            selectQuery.addGroupBy(this.groupBy);
        } else {
            selectQuery.addField(this.idField + " AS id");
            selectQuery.addField(this.labelField + " AS text");
        }
        selectQuery.addWhere(this.idField + " = :id", new Param("id", s));
        return queryOption(selectQuery.toSQL(), selectQuery.toParam());
    }

    @Override
    public List<Option> query(String s, int i) {
        if (isDisabled()) {
            return new ArrayList<>(0);
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        if (this.groupBy != null && !"".equals(this.groupBy)) {
            selectQuery.addField("MAX(" + this.idField + ") AS id");
            selectQuery.addField("MAX(" + this.labelField + ") AS text");
            selectQuery.addOrderBy("MAX(" + this.orderBy + ")", "ASC");
            selectQuery.addGroupBy(this.groupBy);
        } else {
            selectQuery.addField(this.idField + " AS id");
            selectQuery.addField(this.labelField + " AS text");
            selectQuery.addOrderBy(this.orderBy);
        }
        if (this.where != null && !this.where.isEmpty()) {
            for (String where : this.where.values()) {
                if (where != null && !"".equals(where)) {
                    selectQuery.addWhere(where);
                }
            }
        }
        s = StringUtils.trimToEmpty(s);
        if (!"".equals(s)) {
            selectQuery.addWhere("LOWER(" + this.queryField + ") like LOWER(:value)", new Param("value", s + "%"));
        }
        return queryOptions(selectQuery.toSQL(), selectQuery.toParam());
    }

    @Override
    public boolean hasMore(String s, int i) {
        return false;
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        if (isDisabled()) {
            return null;
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        if (this.groupBy != null && !"".equals(this.groupBy)) {
            selectQuery.addField("MAX(" + this.idField + ") AS id");
            selectQuery.addField("MAX(" + this.labelField + ") AS text");
            selectQuery.addOrderBy("MAX(" + this.orderBy + ")", "ASC");
            selectQuery.addGroupBy(this.groupBy);
        } else {
            selectQuery.addField(this.idField + " AS id");
            selectQuery.addField(this.labelField + " AS text");
            selectQuery.addOrderBy(this.orderBy);
        }
        selectQuery.addWhere(this.idField + " = :id", new Param("id", id));
        return queryOption(selectQuery.toSQL(), selectQuery.toParam());
    }

    protected abstract Option queryOption(String query, Map<String, Object> params);

    protected abstract List<Option> queryOptions(String query, Map<String, Object> params);

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void enable() {
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

}
