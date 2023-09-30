package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.jdbc.query.GenericSelectQuery;
import com.senior.cyber.frmk.jdbc.query.SelectQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractJdbcChoiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final String idField;
    protected final String queryField;
    protected final String labelField;
    private final String table;
    private final Map<String, String> join;

    private final Map<String, String> where;
    private final Map<String, Map<String, Object>> whereParam;
    private final String orderBy;
    private boolean disabled = false;
    private String groupBy;

    public AbstractJdbcChoiceProvider(String table, String idField) {
        this(table, idField, idField);
    }

    public AbstractJdbcChoiceProvider(String table, String idField, String queryField) {
        this(table, idField, queryField, queryField);
    }

    public AbstractJdbcChoiceProvider(String table, String idField, String queryField, String orderBy) {
        this(table, idField, queryField, orderBy, queryField);
    }

    public AbstractJdbcChoiceProvider(String table, String idField, String queryField, String orderBy, String labelField) {
        this.table = table;
        this.idField = idField;
        this.orderBy = orderBy;
        this.labelField = labelField;
        this.queryField = queryField;
        this.join = new HashMap<>();
        this.where = new HashMap<>();
        this.whereParam = new HashMap<>();
    }

    public void applyWhere(String key, String sql) {
        this.where.put(key, sql);
    }

    public void applyWhere(String key, String sql, Map<String, Object> params) {
        this.where.put(key, sql);
        this.whereParam.put(key, params);
    }

    public void removeWhere(String key) {
        this.where.remove(key);
        this.whereParam.remove(key);
    }

    public void applyJoin(String key, String join) {
        this.join.put(key, join);
    }

    public void removeJoin(String key) {
        this.join.remove(key);
    }

    public List<Option> toChoice(List<String> ids) {
        if (isDisabled()) {
            return null;
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        if (this.groupBy != null && !this.groupBy.isEmpty()) {
            selectQuery.addField("MAX(" + this.idField + ") AS id");
            selectQuery.addField("MAX(" + this.labelField + ") AS text");
            selectQuery.addGroupBy(this.groupBy);
        } else {
            selectQuery.addField(this.idField + " AS id");
            selectQuery.addField(this.labelField + " AS text");
        }
        selectQuery.addWhere(this.idField + " IN (:id)", Map.of("id", ids));

        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();

        return named.query(selectQuery.toSQL(), selectQuery.toParam(), (rs, rowNum) -> {
            String id = rs.getObject("id", String.class);
            String label = rs.getObject("text", String.class);
            return new Option(id, label);
        });
    }

    public List<Option> searchOption(String term, int i) {
        if (isDisabled()) {
            return new ArrayList<>(0);
        }
        SelectQuery selectQuery = new GenericSelectQuery(this.table);
        if (!this.join.isEmpty()) {
            for (String join : this.join.values()) {
                selectQuery.addJoin(join);
            }
        }
        if (this.groupBy != null && !this.groupBy.isEmpty()) {
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
            for (var where : this.where.entrySet()) {
                if (where.getValue() != null && !where.getValue().isEmpty()) {
                    if (whereParam.get(where.getKey()) == null) {
                        selectQuery.addWhere(where.getValue());
                    } else {
                        selectQuery.addWhere(where.getValue(), whereParam.get(where.getKey()));
                    }
                }
            }
        }
        term = StringUtils.trimToEmpty(term);
        if (!"".equals(term)) {
            selectQuery.addWhere("LOWER(" + this.queryField + ") LIKE LOWER(:term)", Map.of("term", term + "%"));
        }

        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();

        return named.query(selectQuery.toSQL(), selectQuery.toParam(), (rs, rowNum) -> {
            String id = rs.getObject("id", String.class);
            String label = rs.getObject("text", String.class);
            return new Option(id, label);
        });
    }

    protected abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    public boolean hasMore(String term, int i) {
        return false;
    }

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

    public void applyGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

}
