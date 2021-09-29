package com.senior.cyber.frmk.common.model.menu.sidebar;

import com.senior.cyber.frmk.common.model.Badge;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SidebarMenuItem extends SidebarMenu {

    private final String icon;

    private final String text;

    private final Badge badge;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public SidebarMenuItem(String icon, String text, Badge badge, Class<? extends WebPage> page) {
        this.icon = icon;
        this.text = text;
        this.badge = badge;
        this.page = page;
        this.parameters = new PageParameters();
        this.pages.add(page.getName());
    }

    public SidebarMenuItem(String icon, String text, Badge badge, Class<? extends WebPage> page, PageParameters parameters) {
        this.icon = icon;
        this.text = text;
        this.badge = badge;
        this.page = page;
        this.parameters = parameters;
        this.pages.add(page.getName());
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public Badge getBadge() {
        return badge;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

}
