package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.lang.Args;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink
 */
public class OrderByLink extends Link<Void> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * sortable key
     */
    private final String key;

    /**
     * locator for sort state object
     */
    private final ISortStateLocator stateLocator;

    /**
     * Constructor.
     *
     * @param id           the component id of the link
     * @param key          the name of the sortable key this link represents. this value will be used as
     *                     parameter for sort state object methods. sort state object will be located via the
     *                     stateLocator argument.
     * @param stateLocator locator used to locate sort state object that this will use to read/write state of
     *                     sorted properties
     */
    public OrderByLink(final String id, final String key, final ISortStateLocator stateLocator) {
        super(id);

        Args.notNull(key, "key");

        this.key = key;
        this.stateLocator = stateLocator;
    }

    /**
     * @see org.apache.wicket.markup.html.link.Link
     */
    @Override
    public final void onClick() {
        sort();
        onSortChanged();
    }

    /**
     * This method is a hook for subclasses to perform an action after sort has changed
     */
    protected void onSortChanged() {
        // noop
    }

    /**
     * Re-sort data provider according to this link
     *
     * @return this
     */
    public final OrderByLink sort() {
        if (isVersioned()) {
            // version the old state
            addStateChange();
        }

        ISortState state = stateLocator.getSortState();

        // get current sort order
        SortOrder order = state.getKeySortOrder(key);

        // set next sort order
        state.setKeySortOrder(key, nextSortOrder(order));

        return this;
    }

    /**
     * returns the next sort order when changing it
     *
     * @param order previous sort order
     * @return next sort order
     */
    protected SortOrder nextSortOrder(final SortOrder order) {
        // init / flip order
        if (order == SortOrder.NONE) {
            return SortOrder.ASCENDING;
        } else {
            return order == SortOrder.ASCENDING ? SortOrder.DESCENDING : SortOrder.ASCENDING;
        }
    }

}