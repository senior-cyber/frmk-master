package com.senior.cyber.frmk.common.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

public class Breadcrumb implements Serializable {

    private final String text;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public Breadcrumb(String text) {
        this.text = text;
        this.page = null;
        this.parameters = null;
    }

    public Breadcrumb(String text, Class<? extends WebPage> page) {
        this.text = text;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public Breadcrumb(String text, Class<? extends WebPage> page, PageParameters parameters) {
        this.text = text;
        this.page = page;
        this.parameters = parameters;
    }

    public String getText() {
        return text;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

}
