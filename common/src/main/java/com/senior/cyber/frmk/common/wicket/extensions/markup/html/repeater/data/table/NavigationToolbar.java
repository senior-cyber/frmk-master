package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar
 */
public class NavigationToolbar<RowType, CellType extends Serializable> extends AbstractToolbar<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public NavigationToolbar(final DataTable<RowType, CellType> table) {
        super(table);
        setOutputMarkupId(true);
        WebMarkupContainer span = new WebMarkupContainer("span");
        add(span);
        span.add(AttributeModifier.replace("colspan", (IModel<String>) () -> String.valueOf(table.getColumns().size()).intern()));

        span.add(newPagingNavigator("navigator", table));
        span.add(newNavigatorLabel("navigatorLabel", table));
    }

    /**
     * Factory method used to create the paging navigator that will be used by the datatable
     *
     * @param navigatorId component id the navigator should be created with
     * @param table       dataview used by datatable
     * @return paging navigator that will be used to navigate the data table
     */
    protected PagingNavigator newPagingNavigator(final String navigatorId,
                                                 final DataTable<RowType, ? extends CellType> table) {
        return new PagingNavigator(navigatorId, table);
    }

    /**
     * Factory method used to create the navigator label.
     *
     * @param navigatorId component id navigator label should be created with
     * @param table       DataTable used by datatable
     * @return navigator label that will be used to navigate the data table
     */
    protected Component newNavigatorLabel(final String navigatorId, final DataTable<RowType, ? extends CellType> table) {
        return new NavigatorLabel(navigatorId, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(getTable().getPageCount() > 1);
    }

}
