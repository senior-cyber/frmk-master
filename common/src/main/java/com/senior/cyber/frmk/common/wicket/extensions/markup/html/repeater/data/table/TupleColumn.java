package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn
 */
public class TupleColumn<CellType extends Serializable> extends JdbcColumn<CellType> {

    public TupleColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    public TupleColumn(IModel<String> displayModel, String sortKey) {
        super(displayModel, sortKey);
    }

    @Override
    public boolean isSortable() {
        return false;
    }

}