package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DefaultDataGridView<RowType, CellType> extends DataGridView<RowType, CellType> {

    private final DataTable<RowType, CellType> table;

    public DefaultDataGridView(String id, DataTable<RowType, CellType> table, List<? extends IColumn<RowType, CellType>> columns, IDataProvider<RowType> dataProvider) {
        super(id, columns, dataProvider);
        this.table = table;
    }

    @Override
    protected Item<IColumn<RowType, CellType>> newCellItem(String id, int index, IModel<IColumn<RowType, CellType>> model) {
        Item<IColumn<RowType, CellType>> item = table.newCellItem(id, index, model);
        final IColumn<? extends RowType, ? extends CellType> column = table.columns.get(index);
        if (column instanceof IStyledColumn) {
            item.add(new DataTable.CssAttributeBehavior() {

                @Serial
                private static final long serialVersionUID = 1L;

                @Override
                protected String getCssClass() {
                    return ((IStyledColumn<? extends RowType, ? extends CellType>) column).getCssClass();
                }
            });
        }
        return item;
    }

    @Override
    protected Item<RowType> newRowItem(final String id, final int index, final IModel<RowType> model) {
        return table.newRowItem(id, index, model);
    }

}