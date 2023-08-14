package com.senior.cyber.frmk.html.component;

import com.senior.cyber.frmk.html.Astro;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupResourceStream;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.FileResourceStream;

import java.io.File;

@Astro
public class Head extends Panel {

    public Head(String id) {
        super(id);
    }

    public Head(String id, IModel<?> model) {
        super(id, model);
    }

}
