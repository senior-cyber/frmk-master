package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.InputMaskOnDomReady;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.util.Date;

public class DateTimeTextField extends org.apache.wicket.extensions.markup.html.form.DateTextField {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String JAVA_PARTTERN = "dd/MM/yyyy HH:mm";
    private static final String JAVASCRIPT_PARTTERN = "dd/mm/yyyy HH:MM";

    public DateTimeTextField(String id, IModel<Date> model) {
        super(id, model, JAVA_PARTTERN);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("data-inputmask-alias", "datetime"),
                AttributeModifier.replace("data-inputmask-inputformat", JAVASCRIPT_PARTTERN),
                AttributeModifier.replace("data-mask", "data-mask"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new InputMaskOnDomReady("$('#" + markupId + "').inputmask('" + JAVASCRIPT_PARTTERN + "',{ 'placeholder': '" + JAVASCRIPT_PARTTERN + "' })"));
    }
}
