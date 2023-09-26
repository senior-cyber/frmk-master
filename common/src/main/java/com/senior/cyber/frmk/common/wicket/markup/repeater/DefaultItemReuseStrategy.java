package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.util.Iterator;


/**
 * Implementation of <code>IItemReuseStrategy</code> that returns new items every time.
 *
 * @author Igor Vaynberg (ivaynberg)
 * @see org.apache.wicket.markup.repeater.IItemReuseStrategy
 */
public class DefaultItemReuseStrategy<RowType> implements IItemReuseStrategy<RowType> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see org.apache.wicket.markup.repeater.IItemReuseStrategy#getItems(org.apache.wicket.markup.repeater.IItemFactory,
     * java.util.Iterator, java.util.Iterator)
     */
    @Override
    public Iterator<Item<RowType>> getItems(final IItemFactory<RowType> factory, final Iterator<IModel<RowType>> newModels, Iterator<Item<RowType>> existingItems) {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return newModels.hasNext();
            }

            @Override
            public Item<RowType> next() {
                IModel<RowType> model = newModels.next();
                Item<RowType> item = factory.newItem(index, model);
                index++;
                return item;
            }

        };
    }

}
