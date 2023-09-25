package com.senior.cyber.frmk.common.wicket.markup.repeater;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageableItems;
import jakarta.persistence.Tuple;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see org.apache.wicket.markup.repeater.AbstractPageableView
 */
public abstract class AbstractPageableView<RowType> extends RefreshingView<RowType> implements IPageableItems {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPageableView.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Keeps track of the number of items we show per page. The default is Integer.MAX_VALUE which
     * effectively disables paging.
     */
    private int itemsPerPage = Integer.MAX_VALUE;

    /**
     * Keeps track of the current page number.
     */
    private int currentPage;

    /**
     * <code>cachedItemCount</code> is used to cache the call to <code>internalGetItemCount()</code>
     * for the duration of the request because that call can potentially be expensive ( a select
     * count query ) and so we do not want to execute it multiple times.
     */
    private transient Integer cachedItemCount;

    /**
     * Constructor
     *
     * @param id
     * @param model
     * @see org.apache.wicket.Component#Component(String, IModel)
     */
    public AbstractPageableView(String id, IModel<? extends Collection<? extends Tuple>> model) {
        super(id, model);
    }

    /**
     * @see org.apache.wicket.Component#Component(String)
     */
    public AbstractPageableView(String id) {
        super(id);
    }

    /**
     * This method retrieves the subset of models for items in the current page and allows
     * RefreshingView to generate items.
     *
     * @return iterator over models for items in the current page
     */
    @Override
    protected Iterator<IModel<RowType>> getItemModels() {
        int offset = getFirstItemOffset();
        int size = getViewSize();

        Iterator<IModel<RowType>> models = getItemModels(offset, size);

        models = new CappedIteratorAdapter<RowType>(models, size);

        return models;
    }

    /**
     * @see org.apache.wicket.markup.repeater.AbstractRepeater#onBeforeRender()
     */
    @Override
    protected void onBeforeRender() {
        // TODO : for some reason, this run to early to clean item count while wicket will item count. work around comment that code.
        // clearCachedItemCount();
        super.onBeforeRender();
    }

    /**
     * Returns an iterator over models for items in the current page
     *
     * @param offset index of first item in this page
     * @param size   number of items that will be shown in the current page
     * @return an iterator over models for items in the current page
     */
    protected abstract Iterator<IModel<RowType>> getItemModels(int offset, int size);

    // /////////////////////////////////////////////////////////////////////////
    // ITEM COUNT CACHE
    // /////////////////////////////////////////////////////////////////////////

    private void clearCachedItemCount() {
        cachedItemCount = null;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PAGING
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @return maximum number of items that will be shown per page
     */
    @Override
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * Sets the maximum number of items to show per page. The current page will also be set to zero
     *
     * @param items
     */
    @Override
    public final void setItemsPerPage(int items) {
        if (items < 1) {
            throw new IllegalArgumentException("Argument [itemsPerPage] cannot be less than 1");
        }

        if (itemsPerPage != items) {
            if (isVersioned()) {
                addStateChange();
            }
        }

        itemsPerPage = items;

        // because items per page can effect the total number of pages we always
        // reset the current page back to zero
        setCurrentPage(0);
    }

    /**
     * @return total item count
     */
    protected abstract int internalGetItemCount();

    /**
     * Get the row count.
     *
     * @return total item count, but 0 if not visible in the hierarchy
     * @see #getItemCount()
     */
    public final int getRowCount() {
        if (!isVisibleInHierarchy()) {
            return 0;
        }

        return getItemCount();
    }

    /**
     * Get the item count. Since dataprovider.size() could potentially be expensive, the item count
     * is cached.
     *
     * @return the item count
     * @see #getRowCount()
     */
    @Override
    public final int getItemCount() {
        if (cachedItemCount != null) {
            return cachedItemCount;
        }

        int count = internalGetItemCount();

        cachedItemCount = count;
        return count;
    }

    /**
     * @see IPageable#getCurrentPage()
     */
    @Override
    public final int getCurrentPage() {
        int page = currentPage;

        /*
         * trim current page if its out of bounds this can happen if items are added/deleted between
         * requests
         */

        if (page > 0 && page >= getPageCount()) {
            page = Math.max(getPageCount() - 1, 0);
            currentPage = page;
            return page;
        }

        return page;
    }

    /**
     * @see IPageable#setCurrentPage(int)
     */
    @Override
    public final void setCurrentPage(int page) {
        if (currentPage != page) {
            if (isVersioned()) {
                addStateChange();

            }
        }
        currentPage = page;
    }

    /**
     * @see IPageable#getPageCount()
     */
    @Override
    public int getPageCount() {
        int total = getRowCount();
        int itemsPerPage = getItemsPerPage();
        int count = total / itemsPerPage;

        if (itemsPerPage * count < total) {
            count++;
        }

        return count;

    }

    /**
     * @return the index of the first visible item in the view
     */
    public int getFirstItemOffset() {
        return getCurrentPage() * getItemsPerPage();
    }


    /**
     * @return the number of items visible
     */
    public int getViewSize() {
        return Math.min(getItemsPerPage(), getRowCount() - getFirstItemOffset());
    }

    // /////////////////////////////////////////////////////////////////////////
    // HELPER CLASSES
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Iterator adapter that makes sure only the specified max number of items can be accessed from
     * its delegate.
     *
     * @param <T> Model object type
     */
    private static class CappedIteratorAdapter<T> implements Iterator<IModel<T>> {
        private final int max;
        private int index;
        private final Iterator<IModel<T>> delegate;

        /**
         * Constructor
         *
         * @param delegate delegate iterator
         * @param max      maximum number of items that can be accessed.
         */
        public CappedIteratorAdapter(Iterator<IModel<T>> delegate, int max) {
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
        public IModel<T> next() {
            if (index >= max) {
                throw new NoSuchElementException();
            }
            index++;
            return delegate.next();
        }

    }

    /**
     * @see org.apache.wicket.Component#onDetach()
     */
    @Override
    protected void onDetach() {
        clearCachedItemCount();
        super.onDetach();
    }
}