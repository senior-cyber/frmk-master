package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn
 */
public interface IStyledColumn<RowType, CellType> extends IColumn<RowType, CellType> {

    // void populateItem(final Item<IColumn<RowType, ? extends CellType>> cellItem, final String componentId, final IModel<RowType> rowModel);

    /**
     * Returns the css class for this column.
     *
     * @return CSS class name
     */
    String getCssClass();

}
