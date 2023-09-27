package com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serial;
import java.io.Serializable;

public class AjaxNavigationToolbar<RowType, CellType extends Serializable> extends NavigationToolbar<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param table data table this toolbar will be attached to
     */
    public AjaxNavigationToolbar(final DataTable<RowType, CellType> table) {
        super(table);
    }

    /**
     * Factory method used to create the paging navigator that will be used by the datatable.
     *
     * @param navigatorId component id the navigator should be created with
     * @param table       dataview used by datatable
     * @return paging navigator that will be used to navigate the data table
     */
    @Override
    protected PagingNavigator newPagingNavigator(final String navigatorId, final DataTable<RowType, CellType> table) {
        return new AjaxPagingNavigator(navigatorId, table) {

            @Serial
            private static final long serialVersionUID = 1L;

            /**
             * Implement our own ajax event handling in order to update the datatable itself, as the
             * default implementation doesn't support DataViews.
             *
             * @see AjaxPagingNavigator#onAjaxEvent(org.apache.wicket.ajax.AjaxRequestTarget)
             */
            @Override
            protected void onAjaxEvent(final AjaxRequestTarget target) {
                target.add(table);
            }
        };
    }
}
