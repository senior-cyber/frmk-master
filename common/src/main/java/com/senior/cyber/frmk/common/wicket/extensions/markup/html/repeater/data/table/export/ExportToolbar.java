package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IDataExporter;
import org.apache.wicket.model.IModel;


public class ExportToolbar extends org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar {

    /**
     *
     */
    private static final long serialVersionUID = 2499207571533685537L;

    public ExportToolbar(DataTable<?, ?> table, IModel<String> messageModel, IModel<String> fileNameModel) {
        super(table, messageModel, fileNameModel);
        setOutputMarkupId(true);
    }

    public ExportToolbar(DataTable<?, ?> table, IModel<String> fileNameModel) {
        super(table, fileNameModel);
        setOutputMarkupId(true);
    }

    public ExportToolbar(DataTable<?, ?> table) {
        super(table);
        setOutputMarkupId(true);
    }

    @Override
    public ExportToolbar addDataExporter(IDataExporter exporter) {
        return (ExportToolbar) super.addDataExporter(exporter);
    }

    @Override
    public ExportToolbar setMessageModel(IModel<String> messageModel) {
        return (ExportToolbar) super.setMessageModel(messageModel);
    }

    @Override
    public ExportToolbar setFileNameModel(IModel<String> fileNameModel) {
        return (ExportToolbar) super.setFileNameModel(fileNameModel);
    }
}