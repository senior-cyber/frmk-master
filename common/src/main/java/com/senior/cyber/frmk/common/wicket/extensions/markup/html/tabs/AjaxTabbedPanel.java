package com.senior.cyber.frmk.common.wicket.extensions.markup.html.tabs;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.util.List;

public class AjaxTabbedPanel extends org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel<Tab> {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Tab> tabs;

    public AjaxTabbedPanel(String id, List<Tab> tabs) {
        super(id, tabs);
        this.tabs = tabs;
        for (Tab tab : this.tabs) {
            tab.containerPanel = this;
        }
    }

    public AjaxTabbedPanel(String id, List<Tab> tabs, IModel<Integer> model) {
        super(id, tabs, model);
        this.tabs = tabs;
        for (Tab tab : this.tabs) {
            tab.containerPanel = this;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("class", "card card-primary card-outline card-outline-tabs"));
    }

    @Override
    protected String getLastTabCssClass() {
        return "";
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "";
    }

    @Override
    protected WebMarkupContainer newTabsContainer(String id) {
        WebMarkupContainer container = super.newTabsContainer(id);
        container.setRenderBodyOnly(true);
        return container;
    }

    @Override
    protected WebMarkupContainer newLink(String linkId, int index) {
        WebMarkupContainer link = null;
        if (this.tabs.get(index).isEnabled()) {
            link = super.newLink(linkId, index);
        } else {
            link = new WebMarkupContainer(linkId);
            link.add(AttributeModifier.replace("href", "#"));
            link.add(AttributeModifier.replace("class", "nav-link disabled"));
        }
        if (getSelectedTab() == index) {
            if (this.tabs.get(index).isEnabled()) {
                link.add(AttributeModifier.replace("class", "nav-link active tab" + index));
            } else {
                link.add(AttributeModifier.replace("class", "nav-link disabled active tab" + index));
            }
        }
        return link;
    }

    @Override
    public TabbedPanel<Tab> setSelectedTab(int index) {
        if (index < this.tabs.size()) {
            return super.setSelectedTab(index);
        } else {
            return super.setSelectedTab(0);
        }
    }

    public TabbedPanel<Tab> setSelectedTab(String name) {
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tab = this.tabs.get(i);
            if (name != null && name.equals(tab.getName())) {
                return super.setSelectedTab(i);
            }
        }
        return super.setSelectedTab(0);
    }
}
