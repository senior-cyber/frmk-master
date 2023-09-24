package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.Component;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn
 */
public interface IFilteredColumn extends IColumn {

    /**
     * Returns the component used by user to filter the column. If null is returned, no filter will
     * be added.
     *
     * @param componentId component id for returned filter component
     * @param form        FilterForm object for the toolbar. components can use this form's model to access
     *                    properties of the state object (
     *                    <code>PropertyModel(form.getModel(), "property"</code>) or retrieve the
     *                    {@link IFilterStateLocator} object by using {@link FilterForm#getStateLocator()}
     * @return component that will be used to represent a filter for this column, or null if no such
     * component is desired
     */
    Component getFilter(String componentId, FilterForm form);

}
