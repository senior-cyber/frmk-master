package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar
 */
public class HeadersToolbar<RowType, CellType extends Serializable> extends AbstractToolbar<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param table        data table this toolbar will be attached to
     * @param stateLocator locator for the ISortState implementation used by sortable headers
     */
    public HeadersToolbar(final DataTable<RowType, CellType> table, final ISortStateLocator stateLocator) {
        super(table);
        setOutputMarkupId(true);
        RefreshingView<IColumn<? extends RowType,? extends CellType>> headers = new RefreshingView<>("headers") {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected Iterator<IModel<IColumn<? extends RowType, ? extends CellType>>> getItemModels() {
                List<IModel<IColumn<? extends RowType, ? extends CellType>>> columnsModels = new LinkedList<>();

                for (IColumn<? extends RowType, ? extends CellType> column : table.getColumns()) {
                    columnsModels.add(Model.of(column));
                }

                return columnsModels.iterator();
            }

            @Override
            protected void populateItem(Item<IColumn<? extends RowType,? extends CellType>> item) {
                final IColumn<? extends RowType,? extends CellType> column = item.getModelObject();

                WebMarkupContainer header;

                if (column.isSortable()) {
                    header = newSortableHeader("header", column.getSortKey(), stateLocator);
                } else {
                    header = new WebMarkupContainer("header");
                }

                if (column instanceof IStyledColumn) {
                    DataTable.CssAttributeBehavior cssAttributeBehavior = new DataTable.CssAttributeBehavior() {

                        @Serial
                        private static final long serialVersionUID = 1L;

                        @Override
                        protected String getCssClass() {
                            return ((IStyledColumn<RowType, CellType>) column).getCssClass();
                        }
                    };

                    header.add(cssAttributeBehavior);
                }

                if (column.getHeaderColspan() > 1) {
                    header.add(AttributeModifier.replace("colspan", column.getHeaderColspan()));
                    header.add(AttributeModifier.replace("scope", "colgroup"));
                } else {
                    header.add(AttributeModifier.replace("scope", "col"));
                }

                if (column.getHeaderRowspan() > 1) {
                    header.add(AttributeModifier.replace("rowspan", column.getHeaderRowspan()));
                }

                item.add(header);
                item.setRenderBodyOnly(true);
                header.add(column.getHeader("label"));
            }
        };
        add(headers);
    }

    /**
     * Factory method for sortable header components. A sortable header component must have id of
     * <code>headerId</code> and conform to markup specified in <code>HeadersToolbar.html</code>
     *
     * @param headerId header component id
     * @param key      key this header represents
     * @param locator  sort state locator
     * @return created header component
     */
    protected WebMarkupContainer newSortableHeader(final String headerId, final String key,
                                                   final ISortStateLocator locator) {
        return new OrderByBorder(headerId, key, locator) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged() {
                getTable().setCurrentPage(0);
            }
        };
    }

}
