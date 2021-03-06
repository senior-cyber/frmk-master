package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.model.IModel;

public class NoRecordsToolbar extends org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar {

    /**
     *
     */
    private static final long serialVersionUID = 436755211872330679L;

    public NoRecordsToolbar(DataTable<?, ?> table, IModel<String> messageModel) {
        super(table, messageModel);
    }

    public NoRecordsToolbar(DataTable<?, ?> table) {
        super(table);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}