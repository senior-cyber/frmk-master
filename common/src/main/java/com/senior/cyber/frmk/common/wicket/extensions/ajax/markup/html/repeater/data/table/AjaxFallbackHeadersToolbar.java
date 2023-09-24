package com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class AjaxFallbackHeadersToolbar extends HeadersToolbar {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param table
     * @param stateLocator
     */
    public AjaxFallbackHeadersToolbar(final DataTable table, final ISortStateLocator stateLocator) {
        super(table, stateLocator);
        table.setOutputMarkupId(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected WebMarkupContainer newSortableHeader(final String borderId, final String property, final ISortStateLocator locator) {
        return new AjaxFallbackOrderByBorder(borderId, property, locator) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                AjaxFallbackHeadersToolbar.this.updateAjaxAttributes(attributes);
            }

            @Override
            protected void onAjaxClick(final AjaxRequestTarget target) {
                target.add(getTable());
            }

            @Override
            protected void onSortChanged() {
                super.onSortChanged();
                getTable().setCurrentPage(0);
            }
        };
    }

    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    }
}
