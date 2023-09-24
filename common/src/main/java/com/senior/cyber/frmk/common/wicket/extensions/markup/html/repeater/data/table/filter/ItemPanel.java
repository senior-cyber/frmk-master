package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class ItemPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ItemPanel(IModel<?> model) {
        super("item", model);
    }

}
