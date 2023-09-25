package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.markup.repeater.data.IDataProvider;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
 */
public interface ISortableDataProvider<RowType> extends IDataProvider<RowType>, ISortStateLocator {
}
