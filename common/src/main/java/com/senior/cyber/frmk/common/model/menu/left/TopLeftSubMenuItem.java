package com.senior.cyber.frmk.common.model.menu.left;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

public class TopLeftSubMenuItem implements TopLeftSubMenu, Serializable {

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    private final String text;

    public TopLeftSubMenuItem(String text, Class<? extends WebPage> page) {
        this.page = page;
        this.text = text;
        this.parameters = new PageParameters();
    }

    public TopLeftSubMenuItem(String text, Class<WebPage> page, PageParameters parameters) {
        this.page = page;
        this.text = text;
        this.parameters = parameters;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

    public String getText() {
        return text;
    }

}
