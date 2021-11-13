package com.senior.cyber.frmk.common.wicket.markup.html.panel;

import com.senior.cyber.frmk.common.wicket.CssBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class ComponentFeedbackPanel extends org.apache.wicket.markup.html.panel.ComponentFeedbackPanel {

    /**
     *
     */
    private static final long serialVersionUID = 2638799339364446222L;

    protected final String errorClass;

    public ComponentFeedbackPanel(String id, Component filter) {
        this(id, filter, "error invalid-feedback");
    }

    public ComponentFeedbackPanel(String id, Component filter, String errorClass) {
        super(id, filter);
        this.errorClass = errorClass;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(new InternalBehavior(this.errorClass));
    }

    public static class InternalBehavior extends Behavior {

        private final String cssClass;

        public InternalBehavior(String cssClass) {
            this.cssClass = cssClass;
        }

        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            CssBehaviorUtils.appendCssClass(this.cssClass, tag);
        }

    }

}
