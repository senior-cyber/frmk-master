package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionItem implements Serializable, Comparable<ActionItem> {

    /**
     *
     */
    private static final long serialVersionUID = -1297779255222859837L;

    private final String link;

    private final IModel<String> label;

    private final ItemCss css;

    private final Integer order;

    private final boolean icon;

    private final List<String> iconClass = new ArrayList<>();

    public ActionItem(String link, String... iconClass) {
        this.link = link;
        this.label = null;
        this.css = null;
        this.order = 0;
        this.icon = true;
        if (iconClass != null) {
            this.iconClass.addAll(Arrays.asList(iconClass));
        }
    }

    public ActionItem(String link, int order, String... iconClass) {
        this.link = link;
        this.label = null;
        this.css = null;
        this.order = order;
        this.icon = true;
        if (iconClass != null) {
            this.iconClass.addAll(Arrays.asList(iconClass));
        }
    }

    public ActionItem(String link, IModel<String> label) {
        this.link = link;
        this.label = label;
        this.css = ItemCss.INFO;
        this.order = 0;
        this.icon = false;
    }

    public ActionItem(String link, IModel<String> label, int order) {
        this.link = link;
        this.label = label;
        this.css = ItemCss.INFO;
        this.order = order;
        this.icon = false;
    }

    public ActionItem(String link, IModel<String> label, ItemCss css) {
        this.link = link;
        this.label = label;
        this.css = css;
        this.order = 0;
        this.icon = false;
    }

    public boolean isIcon() {
        return icon;
    }

    public List<String> getIconClass() {
        return iconClass;
    }

    public ActionItem(String link, IModel<String> label, ItemCss css, int order) {
        this.link = link;
        this.label = label;
        this.css = css;
        this.order = order;
        this.icon = false;
    }

    public String getLink() {
        return link;
    }

    public IModel<String> getLabel() {
        return label;
    }

    public ItemCss getCss() {
        return css;
    }

    @Override
    public int compareTo(ActionItem o) {
        return this.order.compareTo(o.order);
    }
}
