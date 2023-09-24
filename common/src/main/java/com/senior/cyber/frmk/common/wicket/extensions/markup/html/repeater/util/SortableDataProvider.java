package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
 */
public abstract class SortableDataProvider implements ISortableDataProvider {

    private static final long serialVersionUID = 1L;

    private final SingleSortState state = new SingleSortState();

    @Override
    public final ISortState getSortState() {
        return state;
    }

    public SortParam getSort() {
        return state.getSort();
    }

    public void setSort(final SortParam param) {
        state.setSort(param);
    }

    public void setSort(final String key, final SortOrder order) {
        state.setKeySortOrder(key, order);
    }

}
