package com.senior.cyber.frmk.common.model.menu.right;

import com.senior.cyber.frmk.common.model.Badge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopRightMenuDropdown implements TopRightMenu {

    private final String text;

    private final String icon;

    private final Badge badge;

    private final List<TopRightMenu> children;

    public TopRightMenuDropdown(String text, String icon, Badge badge, TopRightMenu child, TopRightMenu... children) {
        this.text = text;
        this.icon = icon;
        this.badge = badge;
        if (children == null || children.length == 0) {
            this.children = new ArrayList<>(1);
            this.children.add(child);
        } else {
            this.children = new ArrayList<>(children.length + 1);
            this.children.add(child);
            this.children.addAll(Arrays.asList(children));
        }
    }

    public String getText() {
        return text;
    }

    public Badge getBadge() {
        return badge;
    }

    public String getIcon() {
        return icon;
    }

    public List<TopRightMenu> getChildren() {
        return children;
    }
}
