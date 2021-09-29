package com.senior.cyber.frmk.common.wicket.markup.html.panel;

import com.senior.cyber.frmk.common.wicket.CssBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class ContainerFeedbackBehavior extends Behavior {

    private final String errorClass;

    public ContainerFeedbackBehavior() {
        this("is-invalid");
    }

    public ContainerFeedbackBehavior(String errorClass) {
        this.errorClass = errorClass;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        if (component.hasErrorMessage()) {
            CssBehaviorUtils.appendCssClass(this.errorClass, component, tag);
        }
    }

}
