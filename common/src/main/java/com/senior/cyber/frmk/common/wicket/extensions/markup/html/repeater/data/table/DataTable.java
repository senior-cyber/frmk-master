package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import java.util.List;

public class DataTable<T, S> extends AbstractDataTable<T, S> {

    /**
     *
     */
    private static final long serialVersionUID = 2947371120252535158L;

    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of columns
     * @param dataProvider data provider
     * @param rowsPerPage  number of rows per page
     */
    public DataTable(final String id, final List<? extends IColumn<T, S>> columns,
                     final ISortableDataProvider<T, S> dataProvider, final int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        addTopToolbar(new HeadersToolbar<>(this, dataProvider));
        addBottomToolbar(new NoRecordsToolbar(this));
        addBottomToolbar(new NavigationToolbar(this));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(AttributeModifier.replace("class", "table table-bordered table-hover table-striped table-sm"));
    }

    @Override
    protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
        return new OddEvenItem<>(id, index, model);
    }
}