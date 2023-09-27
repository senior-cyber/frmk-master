package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.util.io.IClusterable;

import java.io.Serial;

public class LabelModelObject implements IClusterable {

    @Serial
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