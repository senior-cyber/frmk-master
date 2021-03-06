package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

public class NavigationToolbar extends org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar {

    /**
     *
     */
    private static final long serialVersionUID = 1057961184097803486L;

    public NavigationToolbar(final DataTable<?, ?> table) {
        super(table);
        setOutputMarkupId(true);
    }

    @Override
    protected PagingNavigator newPagingNavigator(String navigatorId, DataTable<?, ?> table) {
        return new PagingNavigator(navigatorId, table);
    }

}