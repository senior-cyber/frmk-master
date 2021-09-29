package com.senior.cyber.frmk.common.model.menu.right;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TopRightMenuSubSimpleItem implements TopRightMenu {

    private final String icon;

    private final String title;

    private final String caption;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public TopRightMenuSubSimpleItem(String icon, String title, String caption, Class<? extends WebPage> page) {
        this.icon = icon;
        this.title = title;
        this.caption = caption;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public TopRightMenuSubSimpleItem(String icon, String title, String caption, Class<? extends WebPage> page, PageParameters parameters) {
        this.icon = icon;
        this.title = title;
        this.caption = caption;
        this.page = page;
        this.parameters = parameters;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

}
