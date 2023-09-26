package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.model.IModel;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CappedIteratorAdapter<RowType> implements Iterator<IModel<RowType>> {

    private final int max;
    private int index;
    private final Iterator<IModel<RowType>> delegate;

    /**
     * Constructor
     *
     * @param delegate delegate iterator
     * @param max      maximum number of items that can be accessed.
     */
    public CappedIteratorAdapter(Iterator<IModel<RowType>> delegate, int max) {
        this.delegate = delegate;
        this.max = max;
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
        return (index < max) && delegate.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public IModel<RowType> next() {
        if (index >= max) {
            throw new NoSuchElementException();
        }
        index++;
        return delegate.next();
    }

}