package com.senior.cyber.frmk.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentHeader implements Serializable {

    private final String caption;

    private final List<Breadcrumb> path;

    public ContentHeader(String caption, Breadcrumb breadcrumb, Breadcrumb... breadcrumbs) {
        this.caption = caption;
        if (breadcrumbs == null || breadcrumbs.length == 0) {
            this.path = new ArrayList<>(1);
            this.path.add(breadcrumb);
        } else {
            this.path = new ArrayList<>(breadcrumbs.length + 1);
            this.path.add(breadcrumb);
            this.path.addAll(Arrays.asList(breadcrumbs));
        }
    }

    public String getCaption() {
        return caption;
    }

    public List<Breadcrumb> getPath() {
        return path;
    }

}
