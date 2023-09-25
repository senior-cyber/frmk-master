package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.CellPanel;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.senior.cyber.frmk.common.wicket.functional.CellSerializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.SerializerFunction;
import jakarta.persistence.Tuple;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn
 */
public class JdbcColumn<CellType extends Serializable> extends AbstractColumn<Tuple, CellType> implements IExportableColumn<Tuple, CellType> {

    private static final long serialVersionUID = 1L;

    private String keyExpression;

    private Class<CellType> typeClass;

    protected SerializerFunction<CellType> serializer;

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

    public Class<CellType> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class<CellType> typeClass) {
        this.typeClass = typeClass;
    }

    public String getKeyExpression() {
        return keyExpression;
    }

    @Override
    public IModel<CellType> getDataModel(IModel<Tuple> rowModel) {
        Tuple tuple = rowModel.getObject();
        return Model.of(tuple.get(this.keyExpression, this.typeClass));
    }

    public void setKeyExpression(String keyExpression) {
        this.keyExpression = keyExpression;
    }

    public void setSerializer(SerializerFunction<CellType> serializer) {
        this.serializer = serializer;
    }

    public void setCellSerializer(CellSerializerFunction<CellType> cellSerializer) {
        this.cellSerializer = cellSerializer;
    }

}
