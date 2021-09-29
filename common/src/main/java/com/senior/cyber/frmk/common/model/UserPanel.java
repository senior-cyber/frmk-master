package com.senior.cyber.frmk.common.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.Serializable;

public class UserPanel implements Serializable {

    private final ResourceReference image;

    private final String text;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public UserPanel(ResourceReference image, String text, Class<? extends WebPage> page) {
        this.image = image;
        this.text = text;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public UserPanel(ResourceReference image, String text, Class<? extends WebPage> page, PageParameters parameters) {
        this.image = image;
        this.text = text;
        this.page = page;
        this.parameters = parameters;
    }

    public ResourceReference getImage() {
        return image;
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
