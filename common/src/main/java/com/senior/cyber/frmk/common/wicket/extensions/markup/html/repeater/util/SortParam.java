package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util;

import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Args;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.util.SortParam
 */
public class SortParam implements IClusterable {

    private static final long serialVersionUID = 1L;

    private final String key;

    private final boolean ascending;

    /**
     * @param key       sort property
     * @param ascending <code>true<code> if sort order is ascending, <code>false</code> if sort order is
     *                  descending
     */
    public SortParam(final String key, final boolean ascending) {
        Args.notNull(key, "key");
        this.key = key;
        this.ascending = ascending;
    }

    /**
     * @return sort property
     */
    public String getKey() {
        return key;
    }

    /**
     * check if sort order is ascending
     *
     * @return <code>true<code> if sort order is ascending, <code>false</code> if sort order is
     * descending
     */
    public boolean isAscending() {
        return ascending;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SortParam)) {
            return false;
        }

        SortParam sortParam = (SortParam) o;

        return (ascending == sortParam.ascending) && key.equals(sortParam.key);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (ascending ? 1 : 0);
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuilder().append("[SortParam key=").append(getKey()).append(" ascending=").append(ascending).append("]").toString();
    }

}
