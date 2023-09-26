package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn
 */
public abstract class AbstractExportableColumn<RowType, CellType extends Serializable> extends AbstractColumn<RowType, CellType> implements IExportableColumn<RowType, CellType> {

    /**
     * Creates a new {@link AbstractExportableColumn} with the provided display model, and without a sort property.
     *
     * @param displayModel The {@link IModel} of the text to be used in the column header.
     */
    public AbstractExportableColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    /**
     * Creates a new {@link AbstractExportableColumn} with the provided display model, and sort property.
     *
     * @param displayModel The {@link IModel} of the text to be used in the column header.
     * @param sortKey      The sort property used by this column.
     */
    public AbstractExportableColumn(IModel<String> displayModel, String sortKey) {
        super(displayModel, sortKey);
    }

    /**
     * Creates a {@link Component} which will be used to display the content of the column in this row.
     * The default implementation simply creates a label with the data model provided.
     *
     * @param componentId The component id of the display component.
     * @param dataModel   The model of the data for this column in the row. This should usually be passed as the model
     *                    of the display component.
     * @return a {@link Component} which will be used to display the content of the column in this row.
     */
    protected Component createDisplayComponent(String componentId, IModel<?> dataModel) {
        return new Label(componentId, dataModel);
    }

    /**
     * Populated the data for this column in the row into the {@code cellItem}.
     * <p>
     * This implementation adds the {@link Component} returned by {@link #createDisplayComponent(java.lang.String, org.apache.wicket.model.IModel) }
     * to the cell.
     *
     * @param cellItem    The cell to be populated.
     * @param componentId The component id to be used for the component that will be added to the cell.
     * @param rowModel    A model of the row data.
     */
    @Override
    public void populateItem(Item<IColumn<RowType, CellType>> cellItem, String componentId, IModel<RowType> rowModel) {
        cellItem.add(createDisplayComponent(componentId, getDataModel(rowModel)));
    }

}
