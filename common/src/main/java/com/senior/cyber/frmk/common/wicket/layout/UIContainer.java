package com.senior.cyber.frmk.common.wicket.layout;

import com.senior.cyber.frmk.common.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;

public class UIContainer extends WebMarkupContainer {

    /**
     *
     */
    private static final long serialVersionUID = 2599850482317800532L;

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