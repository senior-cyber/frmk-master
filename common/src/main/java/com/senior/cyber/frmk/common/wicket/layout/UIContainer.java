package com.senior.cyber.frmk.common.wicket.layout;

import com.senior.cyber.frmk.common.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;

import java.io.Serial;

public class UIContainer extends WebMarkupContainer {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UIContainer(final String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public ComponentFeedbackPanel newFeedback(String id, FormComponent<?> formComponent) {
        ComponentFeedbackPanel feedback = new ComponentFeedbackPanel(id, formComponent);
        add(feedback);
        return feedback;
    }

}