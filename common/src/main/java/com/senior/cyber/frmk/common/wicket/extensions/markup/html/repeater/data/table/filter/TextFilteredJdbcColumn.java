package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn
 */
public class TextFilteredJdbcColumn<T extends Serializable> extends FilteredJdbcColumn<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public TextFilteredJdbcColumn(final IModel<String> displayModel) {
        super(displayModel);
    }

    public TextFilteredJdbcColumn(final IModel<String> displayModel, final String sortKey) {
        super(displayModel, sortKey);
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm form) {
        return new TextFilter(componentId, getFilterModel(form), form);
    }

    protected IModel<String> getFilterModel(final FilterForm form) {
        return new PropertyModel<>(form.getDefaultModel(), this.getKeyExpression());
    }

}