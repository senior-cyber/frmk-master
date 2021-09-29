package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;

public class TextFilter<T> extends AbstractFilter {

    private static final long serialVersionUID = 1L;

    private final TextField<T> filter;

    /**
     * Constructor
     *
     * @param id    component id
     * @param model model for the underlying form component
     * @param form  filter form this filter will be added to
     */
    public TextFilter(final String id, final IModel<Expression<T>> model, final FilterForm<?> form, ExpressionConverter<T> converter) {
        super(id, form);
        this.filter = new TextField<>("filter", model, converter);
        enableFocusTracking(this.filter);
        add(this.filter);
    }

    /**
     * @return underlying {@link TextField} form component that represents this
     * filter
     */
    public final TextField<T> getFilter() {
        return filter;
    }

}
