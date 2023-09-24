package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.util.io.IClusterable;

import java.util.Map;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator
 */
public interface IFilterStateLocator extends IClusterable {

    Map<String, String> getFilterState();

    void setFilterState(Map<String, String> state);

}
