package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid;

import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView
 */
public class DataGridView extends AbstractDataGridView {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * <p>
     * Notice cells are created in the same order as cell populators in the list
     *
     * @param id           component id
     * @param populators   list of ICellPopulators used to populate cells
     * @param dataProvider data provider
     */
    public DataGridView(final String id, final List<? extends ICellPopulator> populators,
                        final IDataProvider dataProvider) {
        super(id, populators, dataProvider);
    }

    /**
     * Returns the list of cell populators
     *
     * @return the list of cell populators
     */
    public List<? extends ICellPopulator> getPopulators() {
        return internalGetPopulators();
    }

    /**
     * Returns the data provider
     *
     * @return data provider
     */
    public IDataProvider getDataProvider() {
        return internalGetDataProvider();
    }

}
