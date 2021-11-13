package com.senior.cyber.frmk.common.wicket.layout;

import com.senior.cyber.frmk.common.wicket.CssBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class UIRow extends WebMarkupContainer {

    /**
     *
     */
    private static final long serialVersionUID = 8898635649074095061L;

    private int size = 0;

    protected UIRow(String id) {
        super(id);
    }

    public static UIRow newUIRow(String id, MarkupContainer parent) {
        UIRow row = new UIRow(id);
        parent.add(row);
        return row;
    }

    public static UIRow newUIRow(String id, WebMarkupContainer parent) {
        UIRow row = new UIRow(id);
        parent.add(row);
        return row;
    }

    public static UIRow newUIRow(String id, Page parent) {
        UIRow row = new UIRow(id);
        parent.add(row);
        return row;
    }

    public UIColumn newUIColumn(String id) {
        return newUIColumn(id, null);
    }

    public UIColumn newUIColumn(String id, Size size) {
        UIColumn block = new UIColumn(id, size);
        add(block);
        if (size != null) {
            this.size = this.size + size.getSize();
        }
        return block;
    }

    public UIColumn lastUIColumn(String id) {
        int remain = 12 - this.size;
        Size temp = null;
        for (Size size : Size.values()) {
            if (size.getSize() == remain) {
                temp = size;
                break;
            }
        }
        UIColumn block = new UIColumn(id, temp);
        add(block);
        this.size = this.size + remain;
        if (temp == Size.Zero_0) {
            block.setVisible(false);
        }
        return block;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setOutputMarkupId(true);
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);
        getWebResponse().write("<div class=\"clearfix\"></div>");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new InternalBehavior("row"));
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
