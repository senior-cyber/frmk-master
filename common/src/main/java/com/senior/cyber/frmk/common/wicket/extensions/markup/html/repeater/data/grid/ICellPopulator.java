package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid;

import jakarta.persistence.Tuple;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
 */
public interface ICellPopulator extends IClusterable, IDetachable {

    void populateItem(final Item<ICellPopulator> cellItem, final String componentId, final IModel<Tuple> rowModel);

}
