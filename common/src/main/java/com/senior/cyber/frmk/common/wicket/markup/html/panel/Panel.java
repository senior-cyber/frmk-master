package com.senior.cyber.frmk.common.wicket.markup.html.panel;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;

import java.io.Serial;

public abstract class Panel extends org.apache.wicket.markup.html.panel.Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    public Panel(String id) {
        super(id);
    }

    public Panel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        onInitData();
        onInitHtml(this);
    }

    protected abstract void onInitData();

    protected abstract void onInitHtml(MarkupContainer body);

}