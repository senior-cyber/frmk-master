package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class HeadersToolbar<S> extends org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar<S> {

    /**
     *
     */
    private static final long serialVersionUID = 4861965573039353068L;

    public <T> HeadersToolbar(AbstractDataTable<T, S> table, ISortStateLocator<S> stateLocator) {
        super(table, stateLocator);
        setOutputMarkupId(true);
    }

    @Override
    protected WebMarkupContainer newSortableHeader(String headerId, S property, ISortStateLocator<S> locator) {
        return new OrderByBorder<>(headerId, property, locator);
    }

}