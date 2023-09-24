package com.senior.cyber.frmk.common.wicket.layout;

import com.senior.cyber.frmk.common.wicket.CssBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class UIColumn extends WebMarkupContainer {

    private static final long serialVersionUID = 1L;

    protected Size size;

    public UIColumn(String id) {
        this(id, null);
    }

    public UIColumn(String id, Size size) {
        super(id);
        this.size = size;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        if (this.size != null) {
            add(new InternalBehavior(this.size.getCss()));
        }
    }

    public UIContainer newUIContainer(String id) {
        UIContainer container = new UIContainer(id);
        add(container);
        return container;
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
