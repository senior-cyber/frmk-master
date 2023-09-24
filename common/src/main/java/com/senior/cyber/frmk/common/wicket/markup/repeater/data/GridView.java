package com.senior.cyber.frmk.common.wicket.markup.repeater.data;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.io.Serializable;

public class GridView<T> extends org.apache.wicket.markup.repeater.data.GridView<T> {

    private static final long serialVersionUID = 1L;

    private PopulateEmptyItem<T> populateEmptyItem;

    private PopulateItem<T> populateItem;

    public GridView(String id, IDataProvider<T> dataProvider) {
        super(id, dataProvider);
    }

    @Override
    protected final void populateEmptyItem(Item<T> item) {
        if (this.populateEmptyItem != null) {
            this.populateEmptyItem.doPopulateEmptyItem(this, item);
        } else {
            throw new WicketRuntimeException("populateEmptyItem is required");
        }
    }

    @Override
    protected final void populateItem(Item<T> item) {
        if (this.populateItem != null) {
            this.populateItem.doPopulateItem(this, item);
        } else {
            throw new WicketRuntimeException("populateItem is required");
        }
    }

    public void setPopulateEmptyItem(PopulateEmptyItem<T> populateEmptyItem) {
        this.populateEmptyItem = populateEmptyItem;
    }

    public void setPopulateItem(PopulateItem<T> populateItem) {
        this.populateItem = populateItem;
    }

    public interface PopulateEmptyItem<T> extends Serializable {
        void doPopulateEmptyItem(GridView<T> self, Item<T> item);
    }

    public interface PopulateItem<T> extends Serializable {
        void doPopulateItem(GridView<T> self, Item<T> item);
    }
}
