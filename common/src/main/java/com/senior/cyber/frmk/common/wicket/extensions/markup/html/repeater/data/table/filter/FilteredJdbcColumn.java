package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.functional.DeserializerFunction;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.JdbcColumn;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn
 */
public abstract class FilteredJdbcColumn<T extends Serializable> extends JdbcColumn<T> implements IFilteredColumn {

    private static final long serialVersionUID = 1L;

    protected DeserializerFunction<T> deserializer;

    public FilteredJdbcColumn(final IModel<String> displayModel, final String sortKey) {
        super(displayModel, sortKey);
    }

    public FilteredJdbcColumn(final IModel<String> displayModel) {
        super(displayModel);
    }

    public void setDeserializer(DeserializerFunction<T> deserializer) {
        this.deserializer = deserializer;
    }

    public DeserializerFunction<T> getDeserializer() {
        return deserializer;
    }

}
