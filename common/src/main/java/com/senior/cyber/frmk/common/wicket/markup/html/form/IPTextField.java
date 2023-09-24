package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.InputMaskOnDomReady;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class IPTextField extends TextField<String> {

    private static final long serialVersionUID = 1L;

    public IPTextField(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("data-inputmask-alias", "ip"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new InputMaskOnDomReady("$('#" + markupId + "').inputmask()"));
    }
}
