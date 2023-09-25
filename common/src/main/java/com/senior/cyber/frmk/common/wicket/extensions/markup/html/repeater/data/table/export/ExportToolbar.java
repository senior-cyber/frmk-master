package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.IResourceStreamWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar
 */
public class ExportToolbar<RowType, CellType> extends AbstractToolbar<RowType, CellType> {

    private static final long serialVersionUID = 1L;

    private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel("datatable.export-to");

    private static final IModel<String> DEFAULT_FILE_NAME_MODEL = new ResourceModel("datatable.export-file-name");

    private final List<IDataExporter<RowType, CellType>> dataExporters = new LinkedList<>();

    private IModel<String> messageModel;

    private IModel<String> fileNameModel;

    /**
     * Creates a new instance with the default message model. This instance will use "export." as the exported
     * file name prefix.
     *
     * @param table The data table this toolbar belongs to.
     */
    public ExportToolbar(final DataTable<RowType, CellType> table) {
        this(table, DEFAULT_MESSAGE_MODEL, DEFAULT_FILE_NAME_MODEL);
        setOutputMarkupId(true);
    }

    /**
     * Creates a new instance with the provided data table and file name model.
     *
     * @param table         The table to which this toolbar belongs.
     * @param fileNameModel The model of the file name. This should exclude the file extensions.
     */
    public ExportToolbar(DataTable<RowType, CellType> table, IModel<String> fileNameModel) {
        this(table, DEFAULT_MESSAGE_MODEL, fileNameModel);
        setOutputMarkupId(true);
    }

    /**
     * Creates a new instance.
     *
     * @param table         The table to which this toolbar belongs.
     * @param messageModel  The model of the export message.
     * @param fileNameModel The model of the file name. This should exclude the file extensions.
     */
    public ExportToolbar(DataTable<RowType, CellType> table, IModel<String> messageModel, IModel<String> fileNameModel) {
        super(table);
        setOutputMarkupId(true);
        setMessageModel(messageModel);
        setFileNameModel(fileNameModel);
    }

    /**
     * Sets the models of the export message displayed in the toolbar.
     *
     * @param messageModel the models of the export message displayed in the toolbar.
     * @return {@code this}, for chaining.
     */
    public ExportToolbar<RowType, CellType> setMessageModel(IModel<String> messageModel) {
        this.messageModel = wrap(Args.notNull(messageModel, "messageModel"));
        return this;
    }

    /**
     * Sets the model of the file name used for the exported data.
     *
     * @param fileNameModel The model of the file name used for the exported data.
     * @return {@code this}, for chaining.
     */
    public ExportToolbar<RowType, CellType> setFileNameModel(IModel<String> fileNameModel) {
        this.fileNameModel = wrap(Args.notNull(fileNameModel, "fileNameModel"));
        return this;
    }

    /**
     * Returns the model of the file name used for the exported data.
     *
     * @return the model of the file name used for the exported data.
     */
    public IModel<String> getFileNameModel() {
        return fileNameModel;
    }

    /**
     * Returns the model of the export message displayed in the toolbar.
     *
     * @return the model of the export message displayed in the toolbar.
     */
    public IModel<String> getMessageModel() {
        return messageModel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebMarkupContainer td = new WebMarkupContainer("td");
        add(td);

        td.add(AttributeModifier.replace("colspan", new IModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return String.valueOf(getTable().getColumns().size()).intern();
            }
        }));

        td.add(new Label("exportTo", messageModel));

        RepeatingView linkContainers = new RepeatingView("linkContainer");
        td.add(linkContainers);

        for (IDataExporter<RowType, CellType> exporter : dataExporters) {
            WebMarkupContainer span = new WebMarkupContainer(linkContainers.newChildId());
            linkContainers.add(span);

            span.add(createExportLink("exportLink", exporter));
        }
    }

    /**
     * Creates a new link to the exported data for the provided {@link IDataExporter}.
     *
     * @param componentId  The component of the link.
     * @param dataExporter The data exporter to use to export the data.
     * @return a new link to the exported data for the provided {@link IDataExporter}.
     */
    protected Component createExportLink(String componentId, final IDataExporter<RowType, CellType> dataExporter) {
        IResource resource = new ResourceStreamResource() {
            /**
             * Set fileName and cacheDuration lazily
             */
            public void respond(Attributes attributes) {
                setFileName(fileNameModel.getObject() + "." + dataExporter.getFileNameExtension());
                setCacheDuration(ExportToolbar.this.getCacheDuration());

                super.respond(attributes);
            }

            @Override
            protected IResourceStream getResourceStream(Attributes attributes) {
                return new DataExportResourceStreamWriter<RowType, CellType>(dataExporter, getTable());
            }
        };

        return new ResourceLink<Void>(componentId, resource).setBody(dataExporter.getDataFormatNameModel());
    }

    /**
     * How long should the export be cached.
     *
     * @return default is {@link Duration#ZERO}
     */
    protected Duration getCacheDuration() {
        return Duration.ZERO;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        calculateVisibility();
    }

    /**
     * This toolbar is only visible if there are rows in the data set and if there are exportable columns in the
     * data table and if there are data exporters added to the toolbar.
     */
    protected void calculateVisibility() {
        final boolean isVisible;
        if (dataExporters.isEmpty()) {
            isVisible = false;
        } else if (getTable().getRowCount() == 0) {
            isVisible = false;
        } else {
            boolean foundExportableColumn = false;
            for (IColumn<RowType, CellType> col : getTable().getColumns()) {
                if (col instanceof IExportableColumn) {
                    foundExportableColumn = true;
                    break;
                }
            }
            isVisible = foundExportableColumn;
        }

        setVisible(isVisible);
    }

    @Override
    protected void onDetach() {
        fileNameModel.detach();
        messageModel.detach();
        super.onDetach();
    }

    /**
     * Adds a {@link IDataExporter} to the list of data exporters to be used in this toolbar.
     *
     * @param exporter The {@link IDataExporter} to add to the toolbar.
     * @return {@code this}, for chaining.
     */
    public ExportToolbar addDataExporter(IDataExporter exporter) {
        Args.notNull(exporter, "exporter");
        dataExporters.add(exporter);
        return this;
    }

    /**
     * An {@link IResourceStreamWriter} which writes the exportable data from a table to an output stream.
     */
    public static class DataExportResourceStreamWriter<RowType, CellType> extends AbstractResourceStreamWriter {
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
        private void exportData(DataTable<RowType, CellType> dataTable, IDataExporter<RowType, CellType> dataExporter, OutputStream outputStream) throws IOException {
            IDataProvider<RowType> dataProvider = dataTable.getDataProvider();
            List<IExportableColumn<RowType, CellType>> exportableColumns = new LinkedList<>();
            for (IColumn<RowType, CellType> col : dataTable.getColumns()) {
                if (col instanceof IExportableColumn) {
                    exportableColumns.add((IExportableColumn<RowType, CellType>) col);
                }
            }
            dataExporter.exportData(dataProvider, exportableColumns, outputStream);
        }
    }
}
