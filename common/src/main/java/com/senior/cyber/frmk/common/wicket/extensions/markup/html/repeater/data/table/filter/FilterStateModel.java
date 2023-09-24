package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.model.util.MapModel;

import java.util.Map;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterStateModel
 */
public class FilterStateModel extends MapModel<String, String> {

    private static final long serialVersionUID = 1L;

    private final IFilterStateLocator locator;

    /**
     * Constructor
     *
     * @param locator IFilterStateLocator implementation used to provide model object for this model
     */
    public FilterStateModel(final IFilterStateLocator locator) {
        this.locator = locator;
    }

    @Override
    public Map<String, String> getObject() {
        return locator.getFilterState();
    }

    @Override
    public void setObject(final Map<String, String> object) {
        locator.setFilterState(object);
    }

}
