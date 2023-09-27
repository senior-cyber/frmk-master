package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.settings.DebugSettings;
import org.apache.wicket.util.string.Strings;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ResponsiveDataTable<RowType, CellType extends Serializable> extends DataTable<RowType, CellType> {

    protected static final int FLAG_OUTPUT_MARKUP_ID = 0x4000;

    protected static final String MARKUP_ID_ATTR_NAME = "id";

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
    public ResponsiveDataTable(final String id, final List<? extends IColumn<RowType, CellType>> columns,
                               final ISortableDataProvider<RowType> dataProvider, final int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        addTopToolbar(new HeadersToolbar<>(this, dataProvider));
        addBottomToolbar(new NoRecordsToolbar<>(this));
        addBottomToolbar(new NavigationToolbar<>(this));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        // We can't try to get the ID from markup. This could be different than
        // id returned from getMarkupId() prior first rendering the component
        // (due to transparent resolvers and borders which break the 1:1
        // component <-> markup relation)
        if (getFlag(FLAG_OUTPUT_MARKUP_ID)) {
            tag.putInternal(MARKUP_ID_ATTR_NAME, getMarkupId());
        }

        DebugSettings debugSettings = getApplication().getDebugSettings();
        String componentPathAttributeName = debugSettings.getComponentPathAttributeName();
        if (!Strings.isEmpty(componentPathAttributeName)) {
            String path = getPageRelativePath();
            path = path.replace("_", "__");
            path = path.replace(':', '_');
            tag.put(componentPathAttributeName, path);
        }

        // The markup sourcing strategy may also want to work on the tag
        getMarkupSourcingStrategy().onComponentTag(this, tag);
    }

    @Override
    protected Item<RowType> newRowItem(final String id, final int index, final IModel<RowType> model) {
        return new OddEvenItem<>(id, index, model);
    }

}