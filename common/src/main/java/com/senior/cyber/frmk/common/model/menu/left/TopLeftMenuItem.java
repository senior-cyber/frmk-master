package com.senior.cyber.frmk.common.model.menu.left;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

public class TopLeftMenuItem implements TopLeftMenu, Serializable {

    private final String text;

    private Class<? extends WebPage> page;

    private PageParameters parameters;

    public TopLeftMenuItem(String text) {
        this.text = text;
    }

    public TopLeftMenuItem(String text, Class<? extends WebPage> page) {
        this.text = text;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public TopLeftMenuItem(String text, Class<WebPage> page, PageParameters parameters) {
        this.text = text;
        this.page = page;
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
