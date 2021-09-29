package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Expression;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.ISortTranslator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.*;

public class ListDataProvider implements IDataProvider<Map<String, Object>>, IFilterStateLocator<Map<String, Expression>>,
        ISortStateLocator<String>,
        ISortableDataProvider<Map<String, Object>, String>,
        IFieldProvider {

    private static final long serialVersionUID = 1L;
    private final SingleSortState<String> sort = new SingleSortState<>();
    /**
     * reference to the list used as dataprovider for the dataview
     */
    private final List<Map<String, Object>> items;
    private Map<String, Expression> state;

    /**
     * Constructs an empty provider. Useful for lazy loading together with
     * {@linkplain #getData()}
     */
    public ListDataProvider() {
        this(new ArrayList<>());
    }

    /**
     * @param items the list used as dataprovider for the dataview
     */
    public ListDataProvider(List<Map<String, Object>> items) {
        if (items == null) {
            throw new IllegalArgumentException("argument [list] cannot be null");
        }
        this.items = items;
        this.state = new HashMap<>();
    }

    /**
     * Subclass to lazy load the list
     *
     * @return The list
     */
    protected List<Map<String, Object>> getData() {
        return items;
    }

    @Override
    public Iterator<Map<String, Object>> iterator(final long first, final long count) {
        List<Map<String, Object>> list = getData();

        long toIndex = first + count;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList((int) first, (int) toIndex).listIterator();
    }

    @Override
    public Map<String, Expression> getFilterState() {
        return this.state;
    }

    @Override
    public void setFilterState(Map<String, Expression> state) {
        this.state = state;
    }

    @Override
    public ISortState<String> getSortState() {
        return this.sort;
    }

    @Override
    public long size() {
        return getData().size();
    }

    @Override
    public IModel<Map<String, Object>> model(Map<String, Object> object) {
        return Model.ofMap(object);
    }

    public List<Map<String, Object>> getItems() {
        return this.items;
    }

    @Override
    public <T> void selectNormalColumn(String key, String column, Convertor<T> convertor) {
    }

    @Override
    public <T> void selectAggregateColumn(String key, String column, Convertor<T> convertor) {
    }

    @Override
    public void registerFilter(String key, Convertor<?> filterTranslator) {
    }

    @Override
    public void registerSort(String key, ISortTranslator sortTranslator) {
    }

}
