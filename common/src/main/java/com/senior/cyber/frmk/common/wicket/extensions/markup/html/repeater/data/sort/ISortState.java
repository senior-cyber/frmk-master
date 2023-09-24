package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState
 */
public interface ISortState extends IClusterable {
    /**
     * Sets sort order of the property
     *
     * @param key   the name of the property to sort on
     * @param order sort order
     */
    void setKeySortOrder(String key, SortOrder order);

    /**
     * Gets the sort order of a property
     *
     * @param key sort property to be checked
     * @return sort order
     */
    SortOrder getKeySortOrder(String key);

}
