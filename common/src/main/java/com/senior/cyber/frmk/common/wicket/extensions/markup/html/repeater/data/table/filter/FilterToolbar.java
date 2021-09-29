package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;

public class FilterToolbar extends org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar {

    /**
     *
     */
    private static final long serialVersionUID = 7585327710822016088L;

    public <T, S, F> FilterToolbar(DataTable<T, S> table, FilterForm<F> form) {
        super(table, form);
        setOutputMarkupId(true);
    }

}