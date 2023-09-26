package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.CellPanel;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.senior.cyber.frmk.common.wicket.functional.CellSerializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.SerializerFunction;
import jakarta.persistence.Tuple;
import lombok.Getter;
import lombok.Setter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn
 */
public class JdbcColumn<CellType extends Serializable> extends AbstractColumn<Tuple, CellType> implements IExportableColumn<Tuple, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private String keyExpression;

    @Getter
    @Setter
    private Class<CellType> typeClass;

    @Setter
    protected SerializerFunction<CellType> serializer;

    @Setter
    protected CellSerializerFunction<CellType> cellSerializer;

    public JdbcColumn(final IModel<String> displayModel) {
        super(displayModel);
    }

    public JdbcColumn(final IModel<String> displayModel, final String sortKey) {
        super(displayModel, sortKey);
    }

    @Override
    public void populateItem(final Item<IColumn<Tuple, CellType>> cellItem, final String componentId, final IModel<Tuple> rowModel) {
        IModel<CellType> m = getDataModel(rowModel);

        ItemPanel itemPanel = this.cellSerializer == null ? null : this.cellSerializer.apply(this.keyExpression, rowModel.getObject(), m.getObject());
        if (itemPanel == null) {
            String text = this.serializer.apply(this.keyExpression, m.getObject());
            cellItem.add(new Label(componentId, text));
        } else {
            CellPanel cell = new CellPanel(componentId);
            cellItem.add(cell);
            cell.add(itemPanel);
        }
    }

    @Override
    public IModel<CellType> getDataModel(IModel<Tuple> rowModel) {
        Tuple tuple = rowModel.getObject();
        return Model.of(tuple.get(this.keyExpression, this.typeClass));
    }

    public void setKeyExpression(String keyExpression) {
        this.keyExpression = keyExpression;
    }

}
