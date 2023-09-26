package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public interface IItemFactory<RowType> {

    /**
     * Factory method for instances of Item. Each generated item must have a unique id with respect
     * to other generated items.
     *
     * @param index the index of the new data item
     * @param model the model for the new data item
     * @return DataItem new DataItem
     */
    Item<RowType> newItem(final int index, final IModel<RowType> model);

}
