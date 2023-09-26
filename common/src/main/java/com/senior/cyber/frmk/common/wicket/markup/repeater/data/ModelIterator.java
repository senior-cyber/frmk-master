package com.senior.cyber.frmk.common.wicket.markup.repeater.data;

import org.apache.wicket.model.IModel;

import java.util.Iterator;

public class ModelIterator<RowType> implements Iterator<IModel<RowType>> {

    private final Iterator<? extends RowType> items;

    private final IDataProvider<RowType> dataProvider;

    private final int max;

    private long index;

    /**
     * Constructor
     *
     * @param dataProvider data provider
     * @param offset       index of first item
     * @param count        max number of items to return
     */
    public ModelIterator(IDataProvider<RowType> dataProvider, int offset, int count) {
        this.dataProvider = dataProvider;
        max = count;
        items = count > 0 ? dataProvider.iterator(offset, count) : null;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return items != null && items.hasNext() && (index < max);
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public IModel<RowType> next() {
        index++;
        return dataProvider.model(items.next());
    }

}