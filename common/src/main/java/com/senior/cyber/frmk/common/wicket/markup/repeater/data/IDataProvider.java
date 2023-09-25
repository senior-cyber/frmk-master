package com.senior.cyber.frmk.common.wicket.markup.repeater.data;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

/**
 * @see org.apache.wicket.markup.repeater.data.IDataProvider
 */
public interface IDataProvider<RowType> extends IDetachable {

    /**
     * Gets an iterator for the subset of total data
     *
     * @param first first row of data
     * @param count minimum number of elements to retrieve
     * @return iterator capable of iterating over {first, first+count} items
     */
    Iterator<? extends RowType> iterator(int first, int count);

    /**
     * Gets total number of items in the collection represented by the DataProvider
     *
     * @return total item count
     */
    int size();

    /**
     * Callback used by the consumer of this data provider to wrap objects retrieved from
     * {@link #iterator(int, int)} with a model (usually a detachable one).
     *
     * @param object the object that needs to be wrapped
     * @return the model representation of the object
     */
    IModel<RowType> model(RowType object);

    @Override
    default void detach() {
    }

}
