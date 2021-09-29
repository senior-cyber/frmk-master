package com.senior.cyber.frmk.common.model.menu.left;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopLeftSubMenuDropdown implements TopLeftSubMenu {

    private final String text;

    private final List<TopLeftSubMenu> children;

    public TopLeftSubMenuDropdown(String text, TopLeftSubMenu child, TopLeftSubMenu... children) {
        this.text = text;
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
        return this.text;
    }

    public List<TopLeftSubMenu> getChildren() {
        return children;
    }

}
