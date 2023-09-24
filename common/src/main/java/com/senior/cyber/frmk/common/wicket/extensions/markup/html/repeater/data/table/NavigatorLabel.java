package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel
 */
public class NavigatorLabel extends Label {

    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     *
     * @param id       The id
     * @param pageable {@link org.apache.wicket.markup.html.navigation.paging.IPageableItems}
     */
    public NavigatorLabel(final String id, final IPageableItems pageable) {
        super(id);
        setDefaultModel(new StringResourceModel("NavigatorLabel", this, new Model<>(new LabelModelObject(pageable))));
    }

    public static class LabelModelObject implements IClusterable {

        private static final long serialVersionUID = 1L;

        private final IPageableItems pageable;

        /**
         * Construct.
         *
         * @param table {@link org.apache.wicket.markup.html.navigation.paging.IPageableItems}
         */
        public LabelModelObject(final IPageableItems table) {
            pageable = table;
        }

        /**
         * @return "z" in "Showing x to y of z"
         */
        public int getOf() {
            return pageable.getItemCount();
        }

        /**
         * @return "x" in "Showing x to y of z"
         */
        public int getFrom() {
            if (getOf() == 0) {
                return 0;
            }
            return pageable.getCurrentPage() * pageable.getItemsPerPage() + 1;
        }

        /**
         * @return "y" in "Showing x to y of z"
         */
        public int getTo() {
            if (getOf() == 0) {
                return 0;
            }
            return Math.min(getOf(), getFrom() + pageable.getItemsPerPage() - 1);
        }
    }

}
