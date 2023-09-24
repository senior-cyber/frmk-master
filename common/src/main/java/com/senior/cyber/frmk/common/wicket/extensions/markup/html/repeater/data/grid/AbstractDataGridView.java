package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid;

import com.senior.cyber.frmk.common.wicket.markup.repeater.RefreshingView;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.DataViewBase;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import jakarta.persistence.Tuple;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.AbstractDataGridView
 */
public abstract class AbstractDataGridView extends DataViewBase {

    private static final long serialVersionUID = 1L;

    private static final String CELL_REPEATER_ID = "cells";
    private static final String CELL_ITEM_ID = "cell";

    private final List<? extends ICellPopulator> populators;

    /**
     * Constructor
     *
     * @param id           component id
     * @param populators   array of ICellPopulator objects that will be used to populate cell items
     * @param dataProvider data provider
     */
    public AbstractDataGridView(final String id, final List<? extends ICellPopulator> populators, final IDataProvider dataProvider) {
        super(id, dataProvider);

        this.populators = populators;
    }

    protected final List<? extends ICellPopulator> internalGetPopulators() {
        return populators;
    }


    /**
     * Factory method for Item container that represents a cell.
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item
     * @return DataItem created DataItem
     * @see Item
     * @see RefreshingView#newItem(String, int, IModel)
     */
    protected Item<ICellPopulator> newCellItem(final String id, final int index, final IModel<ICellPopulator> model) {
        return new Item<>(id, index, model);
    }

    @Override
    protected final Item<Tuple> newItem(final String id, final int index, final IModel<Tuple> model) {
        return newRowItem(id, index, model);
    }

    /**
     * Factory method for Item container that represents a row.
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item.
     * @return DataItem created DataItem
     * @see Item
     * @see RefreshingView#newItem(String, int, IModel)
     */
    protected Item<Tuple> newRowItem(final String id, final int index, final IModel<Tuple> model) {
        return new Item<>(id, index, model);
    }


    /**
     * @see DataViewBase#onDetach()
     */
    @Override
    protected void onDetach() {
        super.onDetach();
        if (populators != null) {
            for (ICellPopulator populator : populators) {
                populator.detach();
            }
        }
    }

    /**
     * @see RefreshingView#populateItem(org.apache.wicket.markup.repeater.Item)
     */
    @Override
    protected final void populateItem(final Item<Tuple> item) {
        RepeatingView cells = new RepeatingView(CELL_REPEATER_ID);
        item.add(cells);

        int populatorsNumber = populators.size();
        for (int i = 0; i < populatorsNumber; i++) {
            ICellPopulator populator = populators.get(i);
            IModel<ICellPopulator> populatorModel = new Model<>(populator);
            Item<ICellPopulator> cellItem = newCellItem(cells.newChildId(), i, populatorModel);
            cells.add(cellItem);

            populator.populateItem(cellItem, CELL_ITEM_ID, item.getModel());

            if (cellItem.get("cell") == null) {
                throw new WicketRuntimeException(populator.getClass().getName() + ".populateItem() failed to add a component with id [" + CELL_ITEM_ID + "] to the provided [cellItem] object. Make sure you call add() on cellItem and make sure you gave the added component passed in 'componentId' id. ( *cellItem*.add(new MyComponent(*componentId*, rowModel) )");
            }
        }

    }

}
