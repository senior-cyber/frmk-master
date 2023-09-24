package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.ColorOnDomReady;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class ColorTextField extends TextField<String> {

    private static final long serialVersionUID = 1L;

    public ColorTextField(String id) {
        super(id, String.class);
    }

    public ColorTextField(String id, IModel<String> model) {
        super(id, model, String.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new ColorOnDomReady("$('#" + markupId + "').colorpicker()"));
    }
}