package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.core.util.string.CssUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.util.string.Strings;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder
 */
public class OrderByBorder extends Border {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SORT_ASCENDING_CSS_CLASS_KEY = CssUtils.key(OrderByLink.class, "ascending");

    public static final String SORT_DESCENDING_CSS_CLASS_KEY = CssUtils.key(OrderByLink.class, "descending");

    public static final String SORT_NONE_CSS_CLASS_KEY = CssUtils.key(OrderByLink.class, "none");

    private final ISortStateLocator stateLocator;

    private final String key;

    /**
     * @param id           see
     *                     {@link OrderByLink#OrderByLink(java.lang.String, java.lang.String, ISortStateLocator) }
     * @param key          see
     *                     {@link OrderByLink#OrderByLink(java.lang.String, java.lang.String, ISortStateLocator) }
     * @param stateLocator see
     *                     {@link OrderByLink#OrderByLink(java.lang.String, java.lang.String, ISortStateLocator) }
     */
    public OrderByBorder(final String id, final String key, final ISortStateLocator stateLocator) {
        super(id);
        setOutputMarkupId(true);

        this.stateLocator = stateLocator;
        this.key = key;

        OrderByLink link = newOrderByLink("orderByLink", key, stateLocator);
        addToBorder(link);
    }

    /**
     * create new sort order toggling link
     *
     * @param id           component id
     * @param key          sort key
     * @param stateLocator sort state locator
     * @return link
     */
    protected OrderByLink newOrderByLink(final String id, final String key, final ISortStateLocator stateLocator) {
        OrderByLink link = new OrderByLink(id, key, stateLocator) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged() {
                OrderByBorder.this.onSortChanged();
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

    /**
     * This method is a hook for subclasses to perform an action after sort has changed
     */
    protected void onSortChanged() {
        // noop
    }

    @Override
    public void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);

        final ISortState sortState = stateLocator.getSortState();

        SortOrder dir = sortState.getKeySortOrder(key);
        String cssClass;
        if (dir == SortOrder.ASCENDING) {
            cssClass = getString(SORT_ASCENDING_CSS_CLASS_KEY);
        } else if (dir == SortOrder.DESCENDING) {
            cssClass = getString(SORT_DESCENDING_CSS_CLASS_KEY);
        } else {
            cssClass = getString(SORT_NONE_CSS_CLASS_KEY);
        }

        if (!Strings.isEmpty(cssClass)) {
            tag.append("class", cssClass, " ");
        }

    }

}
