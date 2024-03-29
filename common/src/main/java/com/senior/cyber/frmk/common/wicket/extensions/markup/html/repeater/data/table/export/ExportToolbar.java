package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
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
import org.apache.wicket.util.resource.IResourceStream;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar
 */
public class ExportToolbar<RowType, CellType extends Serializable> extends AbstractToolbar<RowType, CellType> {

    @Serial
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
    public ExportToolbar(final DataTable<RowType, ? extends CellType> table) {
        this(table, DEFAULT_MESSAGE_MODEL, DEFAULT_FILE_NAME_MODEL);
        setOutputMarkupId(true);
    }

    /**
     * Creates a new instance with the provided data table and file name model.
     *
     * @param table         The table to which this toolbar belongs.
     * @param fileNameModel The model of the file name. This should exclude the file extensions.
     */
    public ExportToolbar(DataTable<RowType, ? extends CellType> table, IModel<String> fileNameModel) {
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
    public ExportToolbar(DataTable<RowType, ? extends CellType> table, IModel<String> messageModel, IModel<String> fileNameModel) {
        super((DataTable<RowType, CellType>) table);
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

            @Serial
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
                return new DataExportResourceStreamWriter<>(dataExporter, getTable());
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
            for (IColumn<RowType, ? extends CellType> col : getTable().getColumns()) {
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
    public ExportToolbar<RowType, CellType> addDataExporter(IDataExporter<RowType, CellType> exporter) {
        Args.notNull(exporter, "exporter");
        dataExporters.add(exporter);
        return this;
    }

}
