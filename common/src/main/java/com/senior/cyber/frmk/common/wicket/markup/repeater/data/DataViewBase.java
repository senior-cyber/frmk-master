package com.senior.cyber.frmk.common.wicket.markup.repeater.data;

import com.senior.cyber.frmk.common.wicket.markup.repeater.AbstractPageableView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import java.io.Serial;
import java.util.Iterator;

/**
 * @see org.apache.wicket.markup.repeater.data.DataViewBase
 */
public abstract class DataViewBase<RowType> extends AbstractPageableView<RowType> {

    @Serial
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
