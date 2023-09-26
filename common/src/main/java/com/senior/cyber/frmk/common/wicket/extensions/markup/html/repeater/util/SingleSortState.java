package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Args;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState
 */
public class SingleSortState implements ISortState, IClusterable {

    @Serial
    private static final long serialVersionUID = 1L;

    SortParam param;

    @Override
    public void setKeySortOrder(final String key, final SortOrder order) {
        Args.notNull(key, "key");
        Args.notNull(order, "order");

        if (order == SortOrder.NONE) {
            if ((param != null) && key.equals(param.getKey())) {
                param = null;
            }
        } else {
            param = new SortParam(key, order == SortOrder.ASCENDING);
        }
    }

    @Override
    public SortOrder getKeySortOrder(final String key) {
        Args.notNull(key, "key");

        if ((param == null) || (!param.getKey().equals(key))) {
            return SortOrder.NONE;
        }
        return param.isAscending() ? SortOrder.ASCENDING : SortOrder.DESCENDING;
    }

    /**
     * @return current sort state
     */
    public SortParam getSort() {
        return param;
    }

    /**
     * Sets the current sort state
     *
     * @param param parameter containing new sorting information
     */
    public void setSort(final SortParam param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "[SingleSortState sort=" + ((param == null) ? "null" : param.toString()) + ']';
    }

}
