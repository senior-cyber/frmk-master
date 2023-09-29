package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable
 */
public class DefaultDataTable<RowType, CellType extends Serializable> extends DataTable<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of columns
     * @param dataProvider data provider
     * @param rowsPerPage  number of rows per page
     */
    public DefaultDataTable(final String id, final List<? extends IColumn<RowType, ? extends CellType>> columns, final ISortableDataProvider<RowType> dataProvider, final int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);

        addTopToolbar(new HeadersToolbar<>(this, dataProvider));
        addBottomToolbar(new NavigationToolbar<>(this));
        addBottomToolbar(new NoRecordsToolbar<>(this));
    }

    @Override
    protected Item<RowType> newRowItem(final String id, final int index, final IModel<RowType> model) {
        return new OddEvenItem<>(id, index, model);
    }

}
