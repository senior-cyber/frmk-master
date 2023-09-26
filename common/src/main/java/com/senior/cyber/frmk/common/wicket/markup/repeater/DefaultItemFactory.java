package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class DefaultItemFactory<RowType> implements IItemFactory<RowType> {

    private RefreshingView<RowType> refreshingView = null;

    public DefaultItemFactory(RefreshingView<RowType> refreshingView) {
        this.refreshingView = refreshingView;
    }

    @Override
    public Item<RowType> newItem(int index, IModel<RowType> model) {
        String id = this.refreshingView.newChildId();
        Item<RowType> item = this.refreshingView.newItem(id, index, model);
        this.refreshingView.populateItem(item);
        return item;
    }

}
