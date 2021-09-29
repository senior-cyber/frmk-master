package com.senior.cyber.frmk.common.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

public class Footer implements Serializable {

    private final String version;

    private final String author;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public Footer(String author, String version, Class<? extends WebPage> page) {
        this.version = version;
        this.author = author;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public Footer(String author, String version, Class<? extends WebPage> page, PageParameters parameters) {
        this.version = version;
        this.author = author;
        this.page = page;
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

}
