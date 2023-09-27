package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel
 */
public class NavigatorLabel extends Label {

    @Serial
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

}
