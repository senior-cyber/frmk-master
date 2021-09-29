package com.senior.cyber.frmk.common.wicket.extensions.markup.html.tabs;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Tab implements ITab {

    protected boolean enabled;

    protected boolean visible;

    protected String title;

    protected String name;

    protected Class<? extends ContentPanel> contentPanel;

    protected Map<String, Object> data;

    protected org.apache.wicket.extensions.markup.html.tabs.TabbedPanel<Tab> containerPanel;

    public Tab(String name, String title, Class<? extends ContentPanel> contentPanel) {
        this(name, title, contentPanel, new HashMap<>());
    }

    public Tab(String name, String title, Class<? extends ContentPanel> contentPanel, Map<String, Object> data) {
        this.title = title;
        this.name = name;
        this.contentPanel = contentPanel;
        this.enabled = true;
        this.visible = true;
        this.data = data;
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        try {
            return this.contentPanel.getConstructor(String.class, String.class, org.apache.wicket.extensions.markup.html.tabs.TabbedPanel.class, Map.class).newInstance(containerId, this.name, this.containerPanel, this.data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of(this.title);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
