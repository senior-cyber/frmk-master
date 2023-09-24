package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import jakarta.persistence.Tuple;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable
 */
public class DefaultDataTable extends DataTable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of columns
     * @param dataProvider data provider
     * @param rowsPerPage  number of rows per page
     */
    public DefaultDataTable(final String id, final List<? extends IColumn> columns, final ISortableDataProvider dataProvider, final int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);

        addTopToolbar(new HeadersToolbar(this, dataProvider));
        addBottomToolbar(new NavigationToolbar(this));
        addBottomToolbar(new NoRecordsToolbar(this));
    }

    @Override
    protected Item<Tuple> newRowItem(final String id, final int index, final IModel<Tuple> model) {
        return new OddEvenItem<>(id, index, model);
    }

}
