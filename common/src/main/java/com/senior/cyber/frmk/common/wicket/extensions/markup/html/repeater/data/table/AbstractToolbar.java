package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
 */
public abstract class AbstractToolbar<RowType, CellType> extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    private final DataTable<RowType, CellType> table;

    /**
     * Constructor
     *
     * @param model model
     * @param table data table this toolbar will be attached to
     */
    public AbstractToolbar(final IModel<?> model, final DataTable<RowType, CellType> table) {
        super(table.newToolbarId(), model);
        this.table = table;
    }

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public AbstractToolbar(final DataTable<RowType, CellType> table) {
        this(null, table);
    }

    /**
     * @return DataTable this toolbar is attached to
     */
    protected DataTable<RowType, CellType> getTable() {
        return table;
    }

}
