package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar
 */
public class FilterToolbar<RowType, CellType> extends AbstractToolbar<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String FILTER_ID = "filter";

    /**
     * Constructor
     *
     * @param table data table this toolbar will be added to
     * @param form  the filter form
     */
    public FilterToolbar(final DataTable<RowType, CellType> table, final FilterForm form) {
        super(table);
        setOutputMarkupId(true);
        Args.notNull(table, "table");

        IModel<List<IColumn<RowType, CellType>>> model = new IModel<>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<IColumn<RowType, CellType>> getObject() {
                return new LinkedList<>(table.getColumns());
            }

        };

        // populate the toolbar with components provided by filtered columns
        ListView<IColumn<RowType, CellType>> filters = new ListView<>("filters", model) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<IColumn<RowType, CellType>> item) {
                final IColumn<RowType, CellType> col = item.getModelObject();
                item.setRenderBodyOnly(true);

                Component filter = null;

                if (col instanceof IFilteredColumn) {
                    IFilteredColumn filteredCol = (IFilteredColumn) col;
                    filter = filteredCol.getFilter(FILTER_ID, form);
                }

                if (filter == null) {
                    filter = new NoFilter(FILTER_ID);
                } else {
                    if (!filter.getId().equals(FILTER_ID)) {
                        throw new IllegalStateException("filter component returned  with an invalid component id. invalid component id [" + filter.getId() + "] required component id [" + getId() + "] generating column [" + col + "] ");
                    }
                }

                if (col instanceof IStyledColumn) {
                    filter.add(new Behavior() {
                        private static final long serialVersionUID = 1L;

                        /**
                         * @see Behavior#onComponentTag(Component, ComponentTag)
                         */
                        @Override
                        public void onComponentTag(final Component component, final ComponentTag tag) {
                            String className = ((IStyledColumn<RowType, CellType>) col).getCssClass();
                            if (!Strings.isEmpty(className)) {
                                tag.append("class", className, " ");
                            }
                        }
                    });
                }

                item.add(filter);
            }
        };
        filters.setReuseItems(true);

        add(filters);
    }

    @Override
    protected void onBeforeRender() {
        if (findParent(FilterForm.class) == null) {
            throw new IllegalStateException("FilterToolbar must be contained within a FilterForm");
        }
        super.onBeforeRender();
    }

}
