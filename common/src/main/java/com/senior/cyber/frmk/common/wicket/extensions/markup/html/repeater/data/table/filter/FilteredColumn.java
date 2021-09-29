package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.provider.BasicTupleElement;
import com.senior.cyber.frmk.common.provider.IFieldProvider;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IHtmlTranslator;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.io.Serializable;

public class FilteredColumn<T extends Serializable> extends AbstractColumn<Tuple, String>
        implements IFilteredColumn<Tuple, String>, IExportableColumn<Tuple, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredColumn.class);

    /**
     *
     */
    private static final long serialVersionUID = -6585236681096888337L;

    protected final String key;

    protected TupleElement<T> field;

    protected IHtmlTranslator<Tuple> html;

    protected ExpressionConverter<T> expressionConverter;

    protected FilteredColumn(IModel<String> displayModel, String key) {
        super(displayModel, key);
        this.key = key;
    }

    public static <T extends Serializable> FilteredColumn<T> normalColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor) {
        FilteredColumn<T> v = new FilteredColumn<>(displayModel, key);
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        v.expressionConverter = new ExpressionConverter<>(key, convertor);
        provider.selectNormalColumn(key, column, convertor);
        provider.registerFilter(key, convertor);
        return v;
    }

    public static <T extends Serializable> FilteredColumn<T> normalColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor, IHtmlTranslator<Tuple> html) {
        FilteredColumn<T> v = new FilteredColumn<>(displayModel, key);
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        v.expressionConverter = new ExpressionConverter<>(key, convertor);
        v.html = html;
        provider.selectNormalColumn(key, column, convertor);
        provider.registerFilter(key, convertor);
        return v;
    }

    public static <T extends Serializable> FilteredColumn<T> aggregateColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor) {
        FilteredColumn<T> v = new FilteredColumn<>(displayModel, key);
        v.expressionConverter = new ExpressionConverter<>(key, convertor);
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        provider.selectAggregateColumn(key, column, convertor);
        provider.registerFilter(key, convertor);
        return v;
    }

    public static <T extends Serializable> FilteredColumn<T> aggregateColumn(IModel<String> displayModel, String key, String column, IFieldProvider provider, Convertor<T> convertor, IHtmlTranslator<Tuple> html) {
        FilteredColumn<T> v = new FilteredColumn<>(displayModel, key);
        v.expressionConverter = new ExpressionConverter<>(key, convertor);
        v.html = html;
        v.field = new BasicTupleElement<>(convertor.getType(), key);
        provider.selectAggregateColumn(key, column, convertor);
        provider.registerFilter(key, convertor);
        return v;
    }

    @Override
    public void populateItem(Item<ICellPopulator<Tuple>> cellItem, String componentId, IModel<Tuple> rowModel) {
        Tuple tuple = rowModel.getObject();
        T value = tuple.get(this.field);
        ItemPanel itemPanel = this.html == null ? null : this.html.htmlColumn(this.key, getDisplayModel(), rowModel.getObject());
        if (itemPanel == null) {
            cellItem.add(new Label(componentId, this.expressionConverter.getConvertor().convertToString(value, null)));
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
        return Model.of(this.expressionConverter.getConvertor().convertToString(value, null));
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        PropertyModel<Expression<T>> model = new PropertyModel<>(form.getDefaultModel(), getPropertyExpression());
        return new TextFilter<>(componentId, model, form, this.expressionConverter);
    }

    @Override
    public String getSortProperty() {
        return this.key;
    }

    public String getPropertyExpression() {
        return this.key;
    }

}
