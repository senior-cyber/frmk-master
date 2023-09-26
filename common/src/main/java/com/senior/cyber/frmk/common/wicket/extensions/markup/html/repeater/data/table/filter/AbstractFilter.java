package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter
 */
public class AbstractFilter extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    private final FilterForm form;

    /**
     * @param id   component id
     * @param form filter form of the filter toolbar
     */
    public AbstractFilter(final String id, final FilterForm form) {
        super(id);
        this.form = form;
    }

    /**
     * Enables the tracking of focus for the specified form component. This allows the filter form
     * to restore focus to the component which caused the form submission. Great for when you are
     * inside a filter textbox and use the enter key to submit the filter.
     *
     * @param fc form component for which focus tracking will be enabled
     */
    protected void enableFocusTracking(final FormComponent<?> fc) {
        form.enableFocusTracking(fc);
    }

    protected IFilterStateLocator getStateLocator() {
        return form.getStateLocator();
    }

    protected IModel<?> getStateModel() {
        return form.getDefaultModel();
    }

    protected Object getState() {
        return form.getDefaultModelObject();
    }

}
