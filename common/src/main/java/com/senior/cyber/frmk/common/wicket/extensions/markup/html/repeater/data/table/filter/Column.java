package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.provider.BasicTupleElement;
import com.senior.cyber.frmk.common.provider.IFieldProvider;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IHtmlTranslator;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import java.io.Serializable;

public class Column<T extends Serializable> extends AbstractColumn<Tuple, String>
        implements IExportableColumn<Tuple, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Column.class);

    /**
     *
     */
    private static final long serialVersionUID = -6585236681096888337L;

    protected final String key;

    protected TupleElement<T> field;

    protected Convertor<T> convertor;

    protected IHtmlTranslator<Tuple> html;

    protected Column(IModel<String> displayModel, String key) {
        super(displayModel, key);
        this.key = key;
    }

    public static <T extends Serializable> Column<T> normalColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor) {
        provider.selectNormalColumn(key, column, convertor);
        Column<T> v = new Column<>(displayModel, key);
        v.convertor = convertor;
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        return v;
    }

    public static <T extends Serializable> Column<T> normalColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor, IHtmlTranslator<Tuple> html) {
        provider.selectNormalColumn(key, column, convertor);
        Column<T> v = new Column<>(displayModel, key);
        v.convertor = convertor;
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        v.html = html;
        return v;
    }

    public static <T extends Serializable> Column<T> aggregateColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor) {
        provider.selectAggregateColumn(key, column, convertor);
        Column<T> v = new Column<>(displayModel, key);
        v.convertor = convertor;
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        return v;
    }

    public static <T extends Serializable> Column<T> aggregateColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor, IHtmlTranslator<Tuple> html) {
        provider.selectAggregateColumn(key, column, convertor);
        Column<T> v = new Column<>(displayModel, key);
        v.convertor = convertor;
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        v.html = html;
        return v;
    }

    @Override
    public void populateItem(Item<ICellPopulator<Tuple>> cellItem, String componentId, IModel<Tuple> rowModel) {
        Tuple tuple = rowModel.getObject();
        T value = tuple.get(this.field);
        ItemPanel itemPanel = this.html == null ? null : this.html.htmlColumn(this.key, getDisplayModel(), rowModel.getObject());
        if (itemPanel == null) {
            cellItem.add(new Label(componentId, this.convertor.convertToString(value, null)));
        } else {
            CellPanel cell = new CellPanel(componentId);
            cellItem.add(cell);
            cell.add(itemPanel);
        }
    }

    @Override
    public IModel<?> getDataModel(IModel<Tuple> rowModel) {
        Tuple tuple = rowModel.getObject();
        T value = tuple.get(this.field);
        return Model.of(value);
    }

    @Override
    public String getSortProperty() {
        return this.key;
    }

    public String getPropertyExpression() {
        return this.key;
    }

}
