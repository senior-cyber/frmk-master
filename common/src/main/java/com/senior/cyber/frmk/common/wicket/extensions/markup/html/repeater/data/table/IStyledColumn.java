package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn
 */
public interface IStyledColumn extends IColumn {
    /**
     * Returns the css class for this column.
     *
     * @return CSS class name
     */
    String getCssClass();
}
