package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import com.senior.cyber.frmk.common.jakarta.persistence.BasicTuple;
import com.senior.cyber.frmk.common.jakarta.persistence.BasicTupleElement;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.JdbcColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.FilteredJdbcColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredJdbcColumn;
import com.senior.cyber.frmk.common.wicket.functional.CellSerializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.DeserializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.FilterFunction;
import com.senior.cyber.frmk.common.wicket.functional.SerializerFunction;
import com.senior.cyber.frmk.common.wicket.model.util.TupleModel;
import com.senior.cyber.frmk.jdbc.query.Param;
import jakarta.persistence.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractJdbcDataProvider extends SortableDataProvider<Tuple> implements IFilterStateLocator {

    public static final String WHERE = "WHERE ";
    public static final String HAVING = "HAVING ";

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJdbcDataProvider.class);

    private Map<String, JdbcColumn<?>> column;
    private Map<String, FilterFunction> callbackFilter;

    protected final Map<String, String> join;
    protected final Map<String, String> userWhere;
    protected final Map<String, List<Param>> userWhereParam;

    protected final Map<String, String> userHaving;
    protected final Map<String, List<Param>> userHavingParam;
    protected Map<String, String> filterState;
    protected Map<String, String> alias;
    protected Map<String, Class<?>> fieldTypes;

    protected String groupBy;
    protected String count;
    protected String from;
    protected boolean enabled = true;

    protected Jdbc jdbc;

    public AbstractJdbcDataProvider(Jdbc jdbc) {
        this(jdbc, "");
    }

    public AbstractJdbcDataProvider(Jdbc jdbc, String from) {
        this.callbackFilter = new LinkedHashMap<>();
        this.jdbc = jdbc;
        this.from = from;
        this.column = new LinkedHashMap<>();
        this.join = new LinkedHashMap<>();
        this.fieldTypes = new LinkedHashMap<>();
        this.alias = new LinkedHashMap<>();
        this.filterState = new LinkedHashMap<>();

        this.userWhere = new LinkedHashMap<>();
        this.userWhereParam = new LinkedHashMap<>();

        this.userHaving = new LinkedHashMap<>();
        this.userHavingParam = new LinkedHashMap<>();
    }

    protected String getFrom() {
        return this.from;
    }

    protected List<String> buildWhere(Map<String, Object> params) {
        List<String> where = new ArrayList<>();
        List<String> userWhere = where(params);
        if (userWhere != null && !userWhere.isEmpty()) {
            where.addAll(userWhere);
        }
        return where;
    }

    public void applyWhere(String key, String sql, List<Param> params) {
        this.userWhere.put(key, sql);
        this.userWhereParam.put(key, params);
    }

    public void removeWhere(String key) {
        this.userWhere.remove(key);
        this.userWhereParam.remove(key);
    }

    public void applyHaving(String key, String sql, List<Param> params) {
        this.userHaving.put(key, sql);
        this.userHavingParam.put(key, params);
    }

    public void removeHaving(String key) {
        this.userHaving.remove(key);
        this.userHavingParam.remove(key);
    }

    public void applyJoin(String key, String join) {
        this.join.put(key, join);
    }

    public void removeJoin(String key) {
        this.join.remove(key);
    }

    public <T extends Serializable> void applySelect(Class<T> fieldType, String key, String sql) {
        this.alias.put(key, sql);
        this.fieldTypes.put(key, fieldType);
    }

    public void removeSelect(String key) {
        this.alias.remove(key);
        this.fieldTypes.remove(key);
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected List<String> buildHaving(Map<String, Object> params) {
        List<String> having = new ArrayList<>();
        List<String> userHaving = having(params);
        if (userHaving != null && !userHaving.isEmpty()) {
            having.addAll(userHaving);
        }
        return having;
    }

    protected String buildOrderBy() {
        String orderBy = null;
        if (getSort() != null) {
            if (getSort().isAscending()) {
                orderBy = asName(getSort().getKey()) + " ASC";
            } else {
                orderBy = asName(getSort().getKey()) + " DESC";
            }
        }
        return orderBy;
    }

    protected String asName(String name) {
        if (this.jdbc == Jdbc.MySql) {
            return "`" + name + "`";
        } else if (this.jdbc == Jdbc.MsSql) {
            return "'" + name + "'";
        } else if (this.jdbc == Jdbc.Oracle) {
            return "\"" + name + "\"";
        } else {
            return name;
        }
    }

    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        for (var entry : this.alias.entrySet()) {
            String alias = entry.getKey();
            String value = entry.getValue();
            fields.add(value + " " + asName(alias));
        }
        return fields;
    }

    public <T extends Serializable> JdbcColumn<T> column(Class<T> fieldType, IModel<String> displayModel, String key, String sql, SerializerFunction<T> serializer) {
        var column = new JdbcColumn<T>(displayModel, key);
        column.setSerializer(serializer);
        column.setTypeClass(fieldType);
        column.setKeyExpression(key);
        this.column.put(key, column);
        this.alias.put(key, sql);
        this.fieldTypes.put(key, fieldType);
        return column;
    }

    public <T extends Serializable> JdbcColumn<T> column(Class<T> fieldType, IModel<String> displayModel, String key, String sql, SerializerFunction<T> serializer, CellSerializerFunction<T> cellSerializer) {
        var column = column(fieldType, displayModel, key, sql, serializer);
        column.setCellSerializer(cellSerializer);
        return column;
    }

    public <T extends Serializable> TextFilteredJdbcColumn<T> filteredColumn(Class<T> fieldType, IModel<String> displayModel, String key, String sql, SerializerFunction<T> serializer, FilterFunction<T> callbackFilter, DeserializerFunction<T> deserializer) {
        var column = new TextFilteredJdbcColumn<T>(displayModel, key);
        column.setSerializer(serializer);
        column.setDeserializer(deserializer);
        column.setKeyExpression(key);
        this.column.put(key, column);
        this.callbackFilter.put(key, callbackFilter);
        this.alias.put(key, sql);
        this.fieldTypes.put(key, fieldType);
        return column;
    }

    public <T extends Serializable> TextFilteredJdbcColumn<T> filteredColumn(Class<T> fieldType, IModel<String> displayModel, String key, String sql, SerializerFunction<T> serializer, FilterFunction<T> callbackFilter, DeserializerFunction<T> deserializer, CellSerializerFunction<T> cellSerializer) {
        var column = filteredColumn(fieldType, displayModel, key, sql, serializer, callbackFilter, deserializer);
        column.setCellSerializer(cellSerializer);
        return column;
    }

    @Override
    public final IModel<Tuple> model(Tuple object) {
        return new TupleModel(object);
    }

    @Override
    public final Map<String, String> getFilterState() {
        return this.filterState;
    }

    @Override
    public final void setFilterState(Map<String, String> filterState) {
        this.filterState = filterState;
    }

    protected List<String> where(Map<String, Object> params) {
        List<String> p = new ArrayList<>(this.userWhere.size());
        for (var i : this.userWhere.entrySet()) {
            p.add(i.getValue());
            for (var pa : this.userWhereParam.get(i.getKey())) {
                params.put(pa.getName(), pa.getValue());
            }
        }
        return p;
    }

    protected List<String> having(Map<String, Object> params) {
        List<String> p = new ArrayList<>(this.userHaving.size());
        for (var i : this.userHaving.entrySet()) {
            p.add(i.getValue());
            for (var pa : this.userHavingParam.get(i.getKey())) {
                params.put(pa.getName(), pa.getValue());
            }
        }
        return p;
    }

    protected QueryBuilder buildQuery(Map<String, Object> params) {
        return buildQuery(params, null);
    }

    protected QueryBuilder buildQuery(Map<String, Object> params, String orderBy) {
        List<String> where = buildWhere(params);
        List<String> having = buildHaving(params);

        if (this.filterState != null && !this.filterState.isEmpty()) {
            for (Map.Entry<String, String> entry : this.filterState.entrySet()) {
                FilteredJdbcColumn column = (FilteredJdbcColumn) this.column.get(entry.getKey());
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    FilterFunction callback = this.callbackFilter.get(entry.getKey());
                    boolean count = orderBy == null;
                    List<String> sqls = (List<String>) callback.apply(count, entry.getKey(), this.alias, params, entry.getValue(), column.getDeserializer());
                    for (String sql : sqls) {
                        if (StringUtils.startsWith(sql, WHERE)) {
                            where.add(StringUtils.substring(sql, WHERE.length()));
                        } else if (StringUtils.startsWith(sql, HAVING)) {
                            having.add(StringUtils.substring(sql, HAVING.length()));
                        }
                    }
                }
            }
        }

        QueryBuilder builder = null;
        if (this.jdbc == Jdbc.MySql) {
            builder = new MySqlQueryBuilder();
        } else if (this.jdbc == Jdbc.MsSql) {
            builder = new MsSqlQueryBuilder();
        } else if (this.jdbc == Jdbc.Oracle) {
            builder = new OracleQueryBuilder();
        }
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
        if (this.groupBy != null && !this.groupBy.isEmpty()) {
            builder.addGroupBy(this.groupBy);
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            builder.addOrderBy(orderBy);
        }
        return builder;
    }

    @Override
    public Iterator<? extends Tuple> iterator(int first, int count) {
        if (!this.enabled) {
            return Collections.emptyIterator();
        }
        Map<String, Object> params = new HashMap<>();
        String orderBy = buildOrderBy();
        QueryBuilder select = buildQuery(params, orderBy);
        select.setLimit(first, count);
        String query = select.toSQL();
        LOGGER.info("SQL : [{}]", query);
        List<Tuple> result = queryIterator(query, params);
        if (result == null || result.isEmpty()) {
            return Collections.emptyIterator();
        } else {
            return result.iterator();
        }
    }

    @Override
    public int size() {
        if (!this.enabled) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        QueryBuilder select = buildQuery(params);
        String countQuery = select.toCountSQL(this.count);
        LOGGER.info("COUNT SQL : [{}]", countQuery);
        return querySize(countQuery, params);
    }

    public void applyCount(String field) {
        this.count = field;
    }

    public void applyGroupBy(String field) {
        this.groupBy = field;
    }

    protected abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    protected List<Tuple> queryIterator(String query, Map<String, Object> params) {
        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();
        return named.query(query, params, (rs, rowNum) -> {
            BasicTuple tuple = new BasicTuple();
            for (var field : this.alias.entrySet()) {
                Object value = rs.getObject(field.getKey(), this.fieldTypes.get(field.getKey()));
                BasicTupleElement<?> element = new BasicTupleElement<>(field.getKey(), this.fieldTypes.get(field.getKey()));
                tuple.set(element, value);
            }
            return tuple;
        });
    }

    protected int querySize(String query, Map<String, Object> params) {
        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();
        List<Integer> sizes = named.queryForList(query, params, Integer.class);
        if (sizes.isEmpty()) {
            return 0;
        }
        return sizes.get(0);
    }

}