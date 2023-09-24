package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar
 */
public class NoRecordsToolbar extends AbstractToolbar {

    private static final long serialVersionUID = 1L;

    private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel("datatable.no-records-found");

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public NoRecordsToolbar(final DataTable table) {
        this(table, DEFAULT_MESSAGE_MODEL);
        setOutputMarkupId(true);
    }

    /**
     * @param table        data table this toolbar will be attached to
     * @param messageModel model that will be used to display the "no records found" message
     */
    public NoRecordsToolbar(final DataTable table, final IModel<String> messageModel) {
        super(table);
        setOutputMarkupId(true);
        WebMarkupContainer td = new WebMarkupContainer("td");
        add(td);

        td.add(AttributeModifier.replace("colspan", new IModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return String.valueOf(table.getColumns().size()).intern();
            }
        }));
        td.add(new Label("msg", messageModel));
    }

    /**
     * Only shows this toolbar when there are no rows
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(getTable().getRowCount() == 0);
    }

}
