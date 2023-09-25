package com.senior.cyber.frmk.common.wicket.markup.repeater.data;

import com.senior.cyber.frmk.common.wicket.markup.repeater.AbstractPageableView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import java.util.Iterator;

/**
 * @see org.apache.wicket.markup.repeater.data.DataViewBase
 */
public abstract class DataViewBase<RowType> extends AbstractPageableView<RowType> {

    private static final long serialVersionUID = 1L;

    private final IDataProvider<RowType> dataProvider;

    /**
     * @param id           component id
     * @param dataProvider data provider
     */
    public DataViewBase(String id, IDataProvider<RowType> dataProvider) {
        super(id);

        this.dataProvider = Args.notNull(dataProvider, "dataProvider");
    }

    /**
     * @return data provider associated with this view
     */
    protected final IDataProvider<RowType> internalGetDataProvider() {
        return dataProvider;
    }


    @Override
    protected final Iterator<IModel<RowType>> getItemModels(int offset, int count) {
        return new ModelIterator<>(internalGetDataProvider(), offset, count);
    }

    /**
     * Helper class that converts input from IDataProvider to an iterator over view items.
     *
     * @author Igor Vaynberg (ivaynberg)
     */
    private static final class ModelIterator<RowType> implements Iterator<IModel<RowType>> {
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

    @Override
    protected final int internalGetItemCount() {
        return internalGetDataProvider().size();
    }

    /**
     * @see AbstractPageableView#onDetach()
     */
    @Override
    protected void onDetach() {
        dataProvider.detach();
        super.onDetach();
    }

}
