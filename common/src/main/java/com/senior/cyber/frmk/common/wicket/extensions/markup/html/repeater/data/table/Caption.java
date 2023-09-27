package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

import java.io.Serial;

public class Caption extends Label {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     *
     * @param id    the component id
     * @param model the caption model
     */
    public Caption(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    protected void onConfigure() {
        setRenderBodyOnly(Strings.isEmpty(getDefaultModelObjectAsString()));

        super.onConfigure();
    }

    @Override
    protected IModel<String> initModel() {
        // don't try to find the model in the parent
        return null;
    }
}