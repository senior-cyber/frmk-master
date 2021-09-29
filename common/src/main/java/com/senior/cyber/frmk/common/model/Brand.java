package com.senior.cyber.frmk.common.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

public class Brand {

    private final String text;

    private final ResourceReference image;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public Brand(String text, ResourceReference image, Class<? extends WebPage> page) {
        this.text = text;
        this.image = image;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public Brand(String text, ResourceReference image, Class<? extends WebPage> page, PageParameters parameters) {
        this.text = text;
        this.image = image;
        this.page = page;
        this.parameters = parameters;
    }

    public String getText() {
        return text;
    }

    public ResourceReference getImage() {
        return image;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }
    
}
