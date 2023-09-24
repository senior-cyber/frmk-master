package com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import jakarta.persistence.Tuple;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import java.util.List;

public class AjaxFallbackDefaultDataTable extends DataTable {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of columns
     * @param dataProvider data provider
     * @param rowsPerPage  number of rows per page
     */
    public AjaxFallbackDefaultDataTable(final String id, final List<? extends IColumn> columns,
                                        final ISortableDataProvider dataProvider, final int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        addToolBars(dataProvider);
    }

    /**
     * Factory method for toolbars
     *
     * @param dataProvider {@link org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider}
     */
    protected void addToolBars(final ISortableDataProvider dataProvider) {
        addTopToolbar(new AjaxFallbackHeadersToolbar(this, dataProvider));
        addBottomToolbar(new AjaxNavigationToolbar(this));
        addBottomToolbar(new NoRecordsToolbar(this));
    }

    @Override
    protected Item<Tuple> newRowItem(final String id, final int index, final IModel<Tuple> model) {
        return new OddEvenItem<>(id, index, model);
    }

}
