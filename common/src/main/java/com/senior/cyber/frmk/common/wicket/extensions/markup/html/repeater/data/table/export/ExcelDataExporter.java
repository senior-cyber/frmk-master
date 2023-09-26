package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.util.Iterator;
import java.util.List;

public class ExcelDataExporter extends AbstractDataExporter {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean exportHeadersEnabled = true;

    public ExcelDataExporter() {
        super(Model.of("Excel"), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
    }

    public ExcelDataExporter setExportHeadersEnabled(boolean exportHeadersEnabled) {
        this.exportHeadersEnabled = exportHeadersEnabled;
        return this;
    }

    public boolean isExportHeadersEnabled() {
        return exportHeadersEnabled;
    }

    protected <T> IModel<T> wrapModel(IModel<T> model) {
        return model;
    }

    @Override
    public <T> void exportData(IDataProvider<T> dataProvider, List<IExportableColumn<T, ?>> columns, OutputStream outputStream) throws IOException {
        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            writeHeaders(columns, sheet.createRow(0));
            writeData(dataProvider, columns, sheet);
            try (OutputStream stream = outputStream) {
                workbook.write(stream);
            }
        }
    }

    private <T> void writeHeaders(List<IExportableColumn<T, ?>> columns, Row row) throws IOException {
        if (exportHeadersEnabled) {
            for (int i = 0; i < columns.size(); i++) {
                IExportableColumn<T, ?> col = columns.get(i);
                IModel<String> displayModel = col.getDisplayModel();
                String display = wrapModel(displayModel).getObject();
                row.createCell(i).setCellValue(display);
            }
        }
    }

    private <T> void writeData(IDataProvider<T> dataProvider, List<IExportableColumn<T, ?>> columns, Sheet sheet) throws IOException {
        long numberOfRows = dataProvider.size();
        Iterator<? extends T> rowIterator = dataProvider.iterator(0, numberOfRows);
        int j = 0;
        if (!exportHeadersEnabled) {
            j = -1;
        }
        while (rowIterator.hasNext()) {
            j++;
            T row = rowIterator.next();

            Row record = sheet.createRow(j);

            for (int i = 0; i < columns.size(); i++) {
                IExportableColumn<T, ?> col = columns.get(i);
                IModel<?> dataModel = col.getDataModel(dataProvider.model(row));

                Object value = wrapModel(dataModel).getObject();
                if (value != null) {
                    Class<?> c = value.getClass();

                    String s;
                    IConverter converter = getConverterLocator().getConverter(c);
                    if (converter == null) {
                        s = value.toString();
                    } else {
                        s = converter.convertToString(value, Session.get().getLocale());
                    }
                    record.createCell(i).setCellValue(s);
                }
            }
        }
    }

    protected IConverterLocator getConverterLocator() {
        return Application.get().getConverterLocator();
    }

}
