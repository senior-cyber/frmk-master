package com.senior.cyber.frmk.common.wicket.markup.html.panel;

import com.senior.cyber.frmk.common.wicket.CssBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class ContainerFeedbackBehavior extends Behavior {

    private final String errorClass;

    private final Component component;

    public ContainerFeedbackBehavior() {
        this("is-invalid");
    }

    public ContainerFeedbackBehavior(Component component) {
        this(component, "is-invalid");
    }

    public ContainerFeedbackBehavior(String errorClass) {
        this.errorClass = errorClass;
        this.component = null;
    }

    public ContainerFeedbackBehavior(Component component, String errorClass) {
        this.errorClass = errorClass;
        this.component = component;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        if (this.component != null) {
            if (this.component.hasErrorMessage()) {
                CssBehaviorUtils.appendCssClass(this.errorClass, tag);
            }
        } else {
            if (component.hasErrorMessage()) {
                CssBehaviorUtils.appendCssClass(this.errorClass, tag);
            }
        }
    }

}
