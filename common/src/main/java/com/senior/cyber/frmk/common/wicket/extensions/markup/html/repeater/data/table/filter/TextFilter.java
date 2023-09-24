package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter
 */
public class TextFilter extends AbstractFilter {

    private static final long serialVersionUID = 1L;

    private final TextField<String> filter;

    /**
     * Constructor
     *
     * @param id    component id
     * @param model model for the underlying form component
     * @param form  filter form this filter will be added to
     */
    public TextFilter(final String id, final IModel<String> model, final FilterForm form) {
        super(id, form);
        filter = new TextField<>("filter", model);
        enableFocusTracking(filter);
        add(filter);
    }

    /**
     * @return underlying {@link TextField} form component that represents this filter
     */
    public final TextField<String> getFilter() {
        return filter;
    }
}
