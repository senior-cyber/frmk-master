package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

public abstract class AbstractDataTable<T, S> extends org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable<T, S> {

    protected static final int FLAG_OUTPUT_MARKUP_ID = 0x4000;

    protected static final String MARKUP_ID_ATTR_NAME = "id";

    /**
     *
     */
    private static final long serialVersionUID = -1703445429005849512L;

    public AbstractDataTable(String id, List<? extends IColumn<T, S>> columns, IDataProvider<T> dataProvider,
                             long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

}