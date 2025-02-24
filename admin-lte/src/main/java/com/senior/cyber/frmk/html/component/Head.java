package com.senior.cyber.frmk.html.component;

import com.senior.cyber.frmk.html.Astro;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

@Astro
public class Head extends Panel {

    public Head(String id) {
        super(id);
    }

    public Head(String id, IModel<?> model) {
        super(id, model);
    }

}
