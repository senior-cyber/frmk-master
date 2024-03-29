package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
 */
public interface ICellPopulator<RowType, CellType> extends IClusterable, IDetachable {

     void populateItem(final Item<IColumn<RowType, ? extends CellType>> cellItem, final String componentId, final IModel<RowType> rowModel);

}
