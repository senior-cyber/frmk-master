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

public abstract class MultipleChoiceProvider<Id, Label> extends IMultipleChoiceProvider<Id, Label> {

    /**
     *
     */
    private static final long serialVersionUID = 266367473515642887L;
    protected final String idField;
    protected final String labelField;
    private final String table;
    private final String queryField;
    private final Map<String, String> where;

    private final Map<String, String> join;
    private final String orderBy;
    private boolean disabled = false;
    private String groupBy;

    public MultipleChoiceProvider(Class<Id> idType, Convertor<Id> idConvertor, Class<Label> labelType, Convertor<Label> labelConvertor, String table, String idField) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, idField);
    }

    public MultipleChoiceProvider(Class<Id> idType, Convertor<Id> idConvertor, Class<Label> labelType, Convertor<Label> labelConvertor, String table, String idField, String queryField) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, queryField, queryField);
    }

    public MultipleChoiceProvider(Class<Id> idType, Convertor<Id> idConvertor, Class<Label> labelType, Convertor<Label> labelConvertor, String table, String idField, String queryField, String orderBy) {
        this(idType, idConvertor, labelType, labelConvertor, table, idField, queryField, orderBy, queryField);
    }

    public MultipleChoiceProvider(Class<Id> idType, Convertor<Id> idConvertor, Class<Label> labelType, Convertor<Label> labelConvertor, String table, String idField, String queryField, String orderBy, String labelField) {
        super(idType, idConvertor, labelType, labelConvertor);
        this.table = table;
        this.idField = idField;
        this.labelField = labelField;
        this.queryField = queryField;
        this.where = new HashMap<>();
        this.join = new HashMap<>();
        this.orderBy = orderBy;
    }

    public void applyJoin(String key, String join) {
        this.join.put(key, join);
    }

    public String removeJoin(String key) {
        return this.join.remove(key);
    }

    public void applyWhere(String key, String filter) {
        this.where.put(key, filter);
    }

    public String removeWhere(String key) {
        return this.where.remove(key);
    }

    @Override
    public List<Option> toChoices(List<String> ids) {
        if (isDisabled()) {
            return new ArrayList<>(0);
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        selectQuery.addWhere(this.idField + " in (:id)", new Param("id", ids));
        if (this.groupBy != null && !"".equals(this.groupBy)) {
            selectQuery.addField("MAX(" + this.idField + ") AS id");
            selectQuery.addField("MAX(" + this.labelField + ") AS text");
            selectQuery.addGroupBy(this.groupBy);
        } else {
            selectQuery.addField(this.idField + " AS id");
            selectQuery.addField(this.labelField + " AS text");
        }
        return queryOptions(selectQuery.toSQL(), selectQuery.toParam());
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
        s = StringUtils.trimToEmpty(s);
        if (!"".equals(s)) {
            selectQuery.addWhere("LOWER(" + this.queryField + ") like LOWER(:value)", new Param("value", s + "%"));
        }
        if (this.where != null && !this.where.isEmpty()) {
            for (String where : this.where.values()) {
                if (where != null && !"".equals(where)) {
                    selectQuery.addWhere(where);
                }
            }
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
