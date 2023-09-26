package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serial;

public abstract class ItemPanel extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemPanel(IModel<?> model) {
        super("item", model);
    }

}
