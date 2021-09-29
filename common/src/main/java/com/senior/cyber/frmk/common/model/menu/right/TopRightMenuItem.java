package com.senior.cyber.frmk.common.model.menu.right;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TopRightMenuItem implements TopRightMenu {

    private Class<? extends WebPage> page;

    private PageParameters parameters;

    private final String text;

    public TopRightMenuItem(String text) {
        this.text = text;
    }

    public TopRightMenuItem(String text, Class<? extends WebPage> page) {
        this.text = text;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public TopRightMenuItem(String text, Class<WebPage> page, PageParameters parameters) {
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
