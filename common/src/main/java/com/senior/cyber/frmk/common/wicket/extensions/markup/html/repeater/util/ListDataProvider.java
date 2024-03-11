package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.JdbcColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.TupleColumn;
import com.senior.cyber.frmk.common.wicket.functional.HtmlSerializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.SerializerFunction;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import com.senior.cyber.frmk.common.wicket.model.util.TupleModel;
import jakarta.persistence.Tuple;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ListDataProvider implements IDataProvider<Tuple>, ISortStateLocator {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListDataProvider.class);

    private List<Tuple> items = new ArrayList<>();

    private final SingleSortState state = new SingleSortState();

    private Map<String, JdbcColumn<? extends Serializable>> column;

    protected Map<String, Class<?>> fieldTypes;

    protected boolean enabled = true;

    public ListDataProvider() {
        this.column = new LinkedHashMap<>();
        this.fieldTypes = new LinkedHashMap<>();
    }

    public void addItem(Tuple item) {
        this.items.add(item);
    }

    @Override
    public Iterator<? extends Tuple> iterator(int first, int count) {
        if (!this.enabled) {
            return Collections.emptyIterator();
        }
        return this.items.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
        if (!this.enabled) {
            return 0;
        }
        return this.items.size();
    }

    @Override
    public IModel<Tuple> model(Tuple object) {
        return new TupleModel(object);
    }

    @Override
    public ISortState getSortState() {
        return this.state;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public <T extends Serializable> TupleColumn<T> column(Class<? extends T> fieldType, IModel<String> displayModel, String key, HtmlSerializerFunction<T> htmlSerializer) {
        var column = new TupleColumn<T>(displayModel, key);
        column.setTypeClass(fieldType);
        column.setKeyExpression(key);
        this.column.put(key, column);
        this.fieldTypes.put(key, fieldType);
        column.setHtmlSerializer(htmlSerializer);
        return column;
    }

    public <T extends Serializable> TupleColumn<T> column(Class<? extends T> fieldType, IModel<String> displayModel, String key, SerializerFunction<T> serializer) {
        var column = new TupleColumn<T>(displayModel, key);
        column.setSerializer(serializer);
        column.setTypeClass(fieldType);
        column.setKeyExpression(key);
        this.column.put(key, column);
        this.fieldTypes.put(key, fieldType);
        return column;
    }

}
