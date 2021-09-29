package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Expression;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.JdbcFilterValue;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.ISortTranslator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.Tuple;
import java.io.Serializable;
import java.util.*;

public abstract class QueryDataProvider extends SortableDataProvider<Tuple, String> implements IFilterStateLocator<Map<String, Expression<?>>>, IFieldProvider {

    /**
     *
     */
    private static final long serialVersionUID = 2453015465083001162L;

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDataProvider.class);
    protected final Map<String, String> join;
    protected final Map<String, String> userWhere;
    protected final Map<String, String> userHaving;
    protected Map<String, Expression<?>> filterState;
    protected Map<String, Convertor<?>> convertor;
    protected Map<String, ISortTranslator> sortTranslator;
    protected Map<String, String> negotiator;
    protected Map<String, String> alias;
    protected List<String> normalColumn;
    protected List<String> aggregateColumn;

    protected String groupBy;
    protected List<String> fields;
    protected String countField;
    protected String from;
    protected boolean enabled = true;

    public QueryDataProvider() {
        this("");
    }

    public QueryDataProvider(String from) {
        this.from = from;
        this.join = new LinkedHashMap<>();
        this.sortTranslator = new LinkedHashMap<>();
        this.convertor = new LinkedHashMap<>();
        this.negotiator = new LinkedHashMap<>();
        this.alias = new LinkedHashMap<>();
        this.normalColumn = new LinkedList<>();
        this.aggregateColumn = new LinkedList<>();
        this.fields = new LinkedList<>();
        this.filterState = new LinkedHashMap<>();
        this.userWhere = new LinkedHashMap<>();
        this.userHaving = new LinkedHashMap<>();
    }

    protected String getFrom() {
        return this.from;
    }

    protected List<String> buildWhere(Map<String, Object> params) {
        List<String> where = new ArrayList<>();
        if (this.filterState != null && !this.filterState.isEmpty()) {
            for (Map.Entry<String, Expression<?>> entry : this.filterState.entrySet()) {
                if (this.normalColumn.contains(entry.getKey())) {
                    Expression<?> expression = entry.getValue();
                    if (expression == null) {
                        continue;
                    }
                    String key = entry.getKey();
                    String column = this.negotiator.get(key);
                    JdbcFilterValue condition = expression.renderToFilter(key, column);
                    if (condition != null && !"".equals(condition.getClause())) {
                        where.add(condition.getClause());
                        params.putAll(condition.getParams());
                    }
                }
            }
        }
        List<String> userWhere = where();
        if (userWhere != null && !userWhere.isEmpty()) {
            where.addAll(userWhere);
        }
        return where;
    }

    public void applyWhere(String key, String filter) {
        this.userWhere.put(key, filter);
    }

    public String removeWhere(String key) {
        return this.userWhere.remove(key);
    }

    public void applyHaving(String key, String filter) {
        this.userHaving.put(key, filter);
    }

    public String removeHaving(String key) {
        return this.userHaving.remove(key);
    }

    public void applyJoin(String key, String join) {
        this.join.put(key, join);
    }

    public String removeJoin(String key) {
        return this.join.remove(key);
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected List<String> buildHaving(Map<String, Object> params) {
        List<String> having = new ArrayList<>();
        if (this.filterState != null && !this.filterState.isEmpty()) {
            for (Map.Entry<String, Expression<?>> entry : this.filterState.entrySet()) {
                if (this.aggregateColumn.contains(entry.getKey())) {
                    Expression<?> expression = entry.getValue();
                    if (expression == null) {
                        continue;
                    }
                    String key = entry.getKey();
                    String column = this.negotiator.get(key);
                    JdbcFilterValue condition = expression.renderToFilter(key, column);
                    if (condition != null && !"".equals(condition.getClause())) {
                        having.add(condition.getClause());
                        params.putAll(condition.getParams());
                    }
                }
            }
        }
        List<String> userHaving = having();
        if (userHaving != null && !userHaving.isEmpty()) {
            having.addAll(userHaving);
        }
        return having;
    }

    protected String buildOrderBy() {
        String orderBy = null;
        if (getSort() != null) {
            if (getSort().isAscending()) {
                if (this.alias.containsKey(getSort().getProperty())) {
                    orderBy = asName(this.alias.get(getSort().getProperty())) + " ASC";
                } else {
                    orderBy = asName(getSort().getProperty()) + " ASC";
                }
            } else {
                if (this.alias.containsKey(getSort().getProperty())) {
                    orderBy = asName(this.alias.get(getSort().getProperty())) + " DESC";
                } else {
                    orderBy = asName(getSort().getProperty()) + " DESC";
                }
            }
        }
        return orderBy;
    }

    protected abstract String asName(String name);

    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.negotiator.entrySet()) {
            String alias = entry.getKey();
            String value = entry.getValue();
            if (this.fields.contains(alias) && !fields.contains(value)) {
                fields.add(value + " " + asName(alias));
            }
        }
        return fields;
    }

    @Override
    public <T> void selectNormalColumn(String key, String column, Convertor<T> convertor) {
        this.fields.add(key);
        this.normalColumn.add(key);
        this.convertor.put(key, convertor);
        this.negotiator.put(key, column);
        this.alias.put(column, key);
    }

    @Override
    public <T> void selectAggregateColumn(String key, String column, Convertor<T> convertor) {
        this.fields.add(column);
        this.aggregateColumn.add(key);
        this.convertor.put(key, convertor);
        this.negotiator.put(key, column);
        this.alias.put(column, key);
    }

    @Override
    public void registerFilter(String key, Convertor<?> convertor) {
        this.convertor.put(key, convertor);
    }

    @Override
    public void registerSort(String key, ISortTranslator sortTranslator) {
        this.sortTranslator.put(key, sortTranslator);
    }

    @Override
    public final IModel<Tuple> model(Tuple object) {
        return new TupleModel(object);
    }

    @Override
    public final Map<String, Expression<?>> getFilterState() {
        return this.filterState;
    }

    @Override
    public final void setFilterState(Map<String, Expression<?>> filterState) {
        this.filterState = filterState;
    }

    protected List<String> where() {
        return new ArrayList<>(this.userWhere.values());
    }

    protected List<String> having() {
        return new ArrayList<>(this.userHaving.values());
    }

    protected QueryBuilder buildQuery(Map<String, Object> params) {
        return buildQuery(params, null);
    }

    protected abstract QueryBuilder buildQuery(Map<String, Object> params, String orderBy);

    @Override
    public Iterator<Tuple> iterator(long first, long count) {
        if (!this.enabled) {
            return Collections.emptyIterator();
        }
        Map<String, Object> params = new HashMap<>();
        String orderBy = buildOrderBy();
        QueryBuilder select = buildQuery(params, orderBy);
        select.setLimit(first, count);
        String query = select.toSQL();
        List<Tuple> result = queryIterator(query, params);
        if (result == null || result.isEmpty()) {
            return Collections.emptyIterator();
        } else {
            return result.iterator();
        }
    }

    @Override
    public long size() {
        if (!this.enabled) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        QueryBuilder select = buildQuery(params);
        String countQuery = select.toCountSQL(this.countField);
        return querySize(countQuery, params);
    }

    @Override
    public void setSort(String column, SortOrder order) {
        super.setSort(column, order);
    }

    public String getCountField() {
        return countField;
    }

    public void setCountField(String countField) {
        this.countField = countField;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    protected abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    protected List<Tuple> queryIterator(String query, Map<String, Object> params) {
        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();
        return named.query(query, params, (rs, rowNum) -> {
            BasicTuple tuple = new BasicTuple();
            for (String field : fields) {
                Object value = rs.getObject(field, this.convertor.get(field).getType());
                BasicTupleElement<?> element = new BasicTupleElement<>(this.convertor.get(field).getType(), field);
                tuple.set(element, value);
            }
            return tuple;
        });
    }

    protected long querySize(String query, Map<String, Object> params) {
        NamedParameterJdbcTemplate named = getNamedParameterJdbcTemplate();
        List<Long> sizes = named.queryForList(query, params, Long.class);
        if (sizes.isEmpty()) {
            return 0;
        }
        return sizes.get(0);
    }

    protected static abstract class QueryBuilder implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -7699684499571047446L;

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

        protected abstract String toSQL();

        protected abstract String toCountSQL(String id);

    }

}