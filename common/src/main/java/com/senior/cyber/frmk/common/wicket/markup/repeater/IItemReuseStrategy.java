package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

import java.util.Iterator;

public interface IItemReuseStrategy<RowType> extends IClusterable {

    /**
     * Returns an iterator over items that will be added to the view. The iterator needs to return
     * all the items because the old ones are removed prior to the new ones added.
     *
     * @param <T>           type of Item
     * @param factory       implementation of IItemFactory
     * @param newModels     iterator over models for items
     * @param existingItems iterator over child items
     * @return iterator over items that will be added after all the old items are moved.
     */
    Iterator<Item<RowType>> getItems(IItemFactory<RowType> factory, Iterator<IModel<RowType>> newModels, Iterator<Item<RowType>> existingItems);
}
