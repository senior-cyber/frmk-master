package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn
 */
public interface IStyledColumn<RowType, CellType extends Serializable> extends IColumn<RowType, CellType> {

    /**
     * Returns the css class for this column.
     *
     * @return CSS class name
     */
    String getCssClass();

}
