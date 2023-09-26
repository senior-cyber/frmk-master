package com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.sort;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.OrderByLink;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.basic.Label;

import java.io.Serial;

public abstract class AjaxFallbackOrderByBorder extends OrderByBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id
     * @param key
     * @param stateLocator
     */
    public AjaxFallbackOrderByBorder(final String id, final String key,
                                     final ISortStateLocator stateLocator) {
        super(id, key, stateLocator);
    }

    @Override
    protected OrderByLink newOrderByLink(String id, String key, ISortStateLocator stateLocator) {
        OrderByLink link = new AjaxOrderByLink("orderByLink", key, stateLocator) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                AjaxFallbackOrderByBorder.this.updateAjaxAttributes(attributes);
            }

            @Override
            protected void onSortChanged() {
                AjaxFallbackOrderByBorder.this.onSortChanged();
            }

            @Override
            public void onClick(final AjaxRequestTarget target) {
                AjaxFallbackOrderByBorder.this.onAjaxClick(target);
            }
        };

        link.setOutputMarkupId(true);
        Label glyphicon = new Label("glyphicon");
        link.add(glyphicon);

        ISortState sortState = stateLocator.getSortState();
        SortOrder dir = sortState.getKeySortOrder(key);

        if (dir == SortOrder.ASCENDING) {
            glyphicon.add(AttributeModifier.replace("class", "fa fa-sort-alpha-down"));
        } else if (dir == SortOrder.DESCENDING) {
            glyphicon.add(AttributeModifier.replace("class", "fa fa-sort-alpha-up"));
        } else {
            glyphicon.setVisible(false);
        }

        return link;
    }

    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    }

    /**
     * This method is a hook for subclasses to perform an action after sort has changed
     */
    @Override
    protected void onSortChanged() {
        // noop
    }

    protected abstract void onAjaxClick(AjaxRequestTarget target);
}