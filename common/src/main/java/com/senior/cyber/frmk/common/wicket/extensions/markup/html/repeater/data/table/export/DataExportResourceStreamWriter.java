package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DataExportResourceStreamWriter<RowType, CellType extends Serializable> extends AbstractResourceStreamWriter {
    private final IDataExporter<RowType, CellType> dataExporter;

    private final DataTable<RowType, CellType> dataTable;

    /**
     * Creates a new instance using the provided {@link IDataExporter} to export data.
     *
     * @param dataExporter The {@link IDataExporter} to use to export data.
     * @param dataTable    The {@link DataTable} from which to export.
     */
    public DataExportResourceStreamWriter(IDataExporter<RowType, CellType> dataExporter, DataTable<RowType, CellType> dataTable) {
        this.dataExporter = dataExporter;
        this.dataTable = dataTable;
    }

    /**
     * Writes the exported data to the output stream. This implementation calls
     * {@link #exportData(DataTable, IDataExporter, java.io.OutputStream) }.
     *
     * @param output The output stream to which to export the data.
     * @throws IOException if an error occurs.
     */
    @Override
    public void write(OutputStream output) throws IOException {
        exportData(dataTable, dataExporter, output);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns the content type returned by {@link IDataExporter#getContentType()}.
     *
     * @return the content type returned by {@link IDataExporter#getContentType()}.
     */
    @Override
    public String getContentType() {
        return dataExporter.getContentType();
    }

    /**
     * Exports the data from the provided data table to the provided output stream.
     * This methods calls {@link IDataExporter#exportData(IDataProvider, java.util.List, java.io.OutputStream) }
     * passing it the {@link IDataProvider} of the {@link DataTable}, and a list of the {@link IExportableColumn}s in the table.
     *
     * @param dataTable    The {@link DataTable} to export.
     * @param dataExporter The {@link IDataExporter} to use to export the data.
     * @param outputStream The {@link OutputStream} to which the data should be exported to.
     * @throws IOException
     */
    private void exportData(DataTable<RowType, ? extends CellType> dataTable, IDataExporter<RowType, CellType> dataExporter, OutputStream outputStream) throws IOException {
        IDataProvider<RowType> dataProvider = dataTable.getDataProvider();
        List<IExportableColumn<RowType, ? extends CellType>> exportableColumns = new LinkedList<>();
        for (IColumn<RowType, ? extends CellType> col : dataTable.getColumns()) {
            if (col instanceof IExportableColumn) {
                exportableColumns.add((IExportableColumn<RowType, ? extends CellType>) col);
            }
        }
        dataExporter.exportData(dataProvider, exportableColumns, outputStream);
    }
}