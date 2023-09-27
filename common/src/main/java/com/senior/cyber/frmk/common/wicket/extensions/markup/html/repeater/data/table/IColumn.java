package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.Component;

import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
 */
public interface IColumn<RowType, CellType extends Serializable> extends ICellPopulator<RowType, CellType> {

    /**
     * Returns the component that will be used as the header for the column.
     * <p>
     * This component will be contained in &lt;span&gt; tags.
     *
     * @param componentId component id for the returned Component
     * @return component that will be used as the header for the column
     */
    Component getHeader(String componentId);

    /**
     * Returns the property that this header sorts on.
     * If {@code null} is returned the header will be not sortable.
     *
     * @return the sort key
     */
    String getSortKey();

    /**
     * Returns true if the header of the column should be sortable
     *
     * @return true if header should be sortable
     */
    default boolean isSortable() {
        return getSortKey() != null;
    }

    /**
     * @return The number of rows the header of this column should span
     */
    default int getHeaderRowspan() {
        return 1;
    }

    /**
     * @return The number of columns the header of this column should span
     */
    default int getHeaderColspan() {
        return 1;
    }

}
