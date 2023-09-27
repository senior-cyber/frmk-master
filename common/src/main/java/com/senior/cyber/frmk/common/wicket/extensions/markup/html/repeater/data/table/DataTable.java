package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageableItems;
import com.senior.cyber.frmk.common.wicket.markup.repeater.IItemReuseStrategy;
import com.senior.cyber.frmk.common.wicket.markup.repeater.RefreshingView;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ColGroup;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
 */
public class DataTable<RowType, CellType extends Serializable> extends Panel implements IPageableItems {

    @Serial
    private static final long serialVersionUID = 1L;

    private final DataGridView<RowType, CellType> datagrid;

    private final WebMarkupContainer body;

    protected final List<? extends IColumn<RowType, ? extends CellType>> columns;

    private final ToolbarsContainer topToolbars;

    private final ToolbarsContainer bottomToolbars;

    private final Caption caption;

    private final ColGroup colGroup;

    private long toolbarIdCounter;

    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of IColumn objects
     * @param dataProvider imodel for data provider
     * @param rowsPerPage  number of rows per page
     */
    public DataTable(final String id, final List<? extends IColumn<RowType, CellType>> columns, final IDataProvider<RowType> dataProvider, final int rowsPerPage) {
        super(id);
        setOutputMarkupId(true);
        add(AttributeModifier.replace("class", "table table-bordered table-hover table-striped table-sm"));
        Args.notNull(columns, "columns");

        this.columns = columns;
        this.caption = new Caption("caption", getCaptionModel());
        add(caption);
        this.colGroup = new ColGroup("colGroup");
        add(colGroup);
        body = newBodyContainer("body");
        datagrid = new DefaultDataGridView<>("rows", this, columns, dataProvider);
        datagrid.setItemsPerPage(rowsPerPage);
        body.add(datagrid);
        add(body);
        topToolbars = new ToolbarsContainer("topToolbars");
        bottomToolbars = new ToolbarsContainer("bottomToolbars");
        add(topToolbars);
        add(bottomToolbars);
    }

    /**
     * Returns the model for table's caption. The caption wont be rendered if the model has empty
     * value.
     *
     * @return the model for table's caption
     */
    protected IModel<String> getCaptionModel() {
        return null;
    }

    public final ColGroup getColGroup() {
        return colGroup;
    }

    /**
     * Create the MarkupContainer for the <tbody> tag. Developers may subclass it to provide their own
     * (modified) implementation.
     *
     * @param id
     * @return A new markup container
     */
    protected WebMarkupContainer newBodyContainer(final String id) {
        return new WebMarkupContainer(id);
    }

    public final void setTableBodyCss(final String cssStyle) {
        body.add(AttributeModifier.replace("class", cssStyle));
    }

    /**
     * Adds a toolbar to the datatable that will be displayed after the data
     *
     * @param toolbar toolbar to be added
     * @see AbstractToolbar
     */
    public void addBottomToolbar(final AbstractToolbar<RowType, CellType> toolbar) {
        addToolbar(toolbar, bottomToolbars);
    }

    /**
     * Adds a toolbar to the datatable that will be displayed before the data
     *
     * @param toolbar toolbar to be added
     * @see AbstractToolbar
     */
    public void addTopToolbar(final AbstractToolbar<RowType, CellType> toolbar) {
        addToolbar(toolbar, topToolbars);
    }

    /**
     * @return the container with the toolbars at the top
     */
    public final WebMarkupContainer getTopToolbars() {
        return topToolbars;
    }

    /**
     * @return the container with the toolbars at the bottom
     */
    public final WebMarkupContainer getBottomToolbars() {
        return bottomToolbars;
    }

    /**
     * @return the container used for the table body
     */
    public final WebMarkupContainer getBody() {
        return body;
    }

    /**
     * @return the component used for the table caption
     */
    public final Caption getCaption() {
        return caption;
    }

    /**
     * @return dataprovider
     */
    public final IDataProvider<RowType> getDataProvider() {
        return datagrid.getDataProvider();
    }

    public final List<? extends IColumn<RowType, ? extends CellType>> getColumns() {
        return columns;
    }

    /**
     * @see IPageable#getCurrentPage()
     */
    @Override
    public final int getCurrentPage() {
        return datagrid.getCurrentPage();
    }

    /**
     * @see IPageable#getPageCount()
     */
    @Override
    public final int getPageCount() {
        return datagrid.getPageCount();
    }

    /**
     * @return total number of rows in this table
     */
    public final int getRowCount() {
        return datagrid.getRowCount();
    }

    /**
     * @return number of rows per page
     */
    @Override
    public final int getItemsPerPage() {
        return datagrid.getItemsPerPage();
    }

    /**
     * @see IPageable#setCurrentPage(int)
     */
    @Override
    public final void setCurrentPage(final int page) {
        datagrid.setCurrentPage(page);
        onPageChanged();
    }


    /**
     * Sets the item reuse strategy. This strategy controls the creation of {@link Item}s.
     *
     * @param strategy item reuse strategy
     * @return this for chaining
     * @see RefreshingView#setItemReuseStrategy(IItemReuseStrategy)
     * @see IItemReuseStrategy
     */
    public final DataTable<RowType, CellType> setItemReuseStrategy(final IItemReuseStrategy<RowType> strategy) {
        datagrid.setItemReuseStrategy(strategy);
        return this;
    }

    /**
     * Sets the number of items to be displayed per page
     *
     * @param items number of items to display per page
     */
    @Override
    public void setItemsPerPage(final int items) {
        datagrid.setItemsPerPage(items);
    }

    /**
     * @see IPageableItems#getItemCount()
     */
    @Override
    public int getItemCount() {
        return datagrid.getItemCount();
    }

    private void addToolbar(final AbstractToolbar<RowType, CellType> toolbar, final ToolbarsContainer container) {
        Args.notNull(toolbar, "toolbar");

        container.getRepeatingView().add(toolbar);
    }

    /**
     * Factory method for Item container that represents a cell in the underlying DataGridView
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item
     * @return DataItem created DataItem
     * @see Item
     */
    protected Item<IColumn<RowType, ? extends CellType>> newCellItem(final String id, final int index, final IModel<IColumn<RowType, ? extends CellType>> model) {
        return new Item<>(id, index, model);
    }

    /**
     * Factory method for Item container that represents a row in the underlying DataGridView
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item.
     * @return DataItem created DataItem
     * @see Item
     */
    protected Item<RowType> newRowItem(final String id, final int index, final IModel<RowType> model) {
        return new OddEvenItem<>(id, index, model);
    }

    /**
     * @see Component#onDetach()
     */
    @Override
    protected void onDetach() {
        super.onDetach();

        for (IColumn<RowType, ? extends CellType> column : columns) {
            column.detach();
        }
    }

    /**
     * Event listener for page-changed event
     */
    protected void onPageChanged() {
        // noop
    }

    /**
     * @see AbstractToolbar
     */
    String newToolbarId() {
        toolbarIdCounter++;
        return String.valueOf(toolbarIdCounter).intern();
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        checkComponentTag(tag, "table");
        super.onComponentTag(tag);
    }

}
