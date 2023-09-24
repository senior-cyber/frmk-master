package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.RichTextOnDomReady;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

public class RichTextField extends TextArea<String> {

    private static final long serialVersionUID = 1L;

    public RichTextField(String id) {
        super(id);
    }

    public RichTextField(String id, IModel<String> model) {
        super(id, model);
    }

    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new RichTextOnDomReady("$('#" + markupId + "').summernote()"));
    }
}