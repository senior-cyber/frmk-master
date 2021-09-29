package com.senior.cyber.frmk.common.wicket.extensions.markup.html.tabs;

import com.senior.cyber.frmk.common.wicket.markup.html.panel.Panel;

import java.util.Map;

public abstract class ContentPanel extends Panel {

    protected String name;

    protected Map<String, Object> data;

    protected org.apache.wicket.extensions.markup.html.tabs.TabbedPanel<Tab> containerPanel;

    public ContentPanel(String id, String name, org.apache.wicket.extensions.markup.html.tabs.TabbedPanel<Tab> containerPanel, Map<String, Object> data) {
        super(id);
        this.containerPanel = containerPanel;
        this.name = name;
        this.data = data;
    }

    protected String getName() {
        return this.name;
    }

    protected void setSelectedTab(int index) {
        this.containerPanel.setSelectedTab(index);
    }

    protected void setSelectedTab(String name) {
        if (this.containerPanel.getTabs() != null) {
            for (int i = 0; i < this.containerPanel.getTabs().size(); i++) {
                Tab tab = this.containerPanel.getTabs().get(0);
                if (name.equals(tab.getName())) {
                    this.containerPanel.setSelectedTab(i);
                    return;
                }
            }
        }
    }

}
