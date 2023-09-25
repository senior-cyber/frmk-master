package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import jakarta.persistence.Tuple;
import org.apache.wicket.model.IModel;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn
 */
public interface IExportableColumn<RowType, CellType> extends IColumn<RowType, CellType> {

    /**
     * Returns an {@link IModel} of the data displayed by this column for the {@code rowModel} provided.
     *
     * @param rowModel An {@link IModel} of the row data.
     * @return an {@link IModel} of the data displayed by this column for the {@code rowModel} provided.
     */
    IModel<CellType> getDataModel(IModel<RowType> rowModel);

    /**
     * Returns a model of the column header. The content of this model is used as a heading for the column
     * when it is exported.
     *
     * @return a model of the column header.
     */
    IModel<String> getDisplayModel();

}
