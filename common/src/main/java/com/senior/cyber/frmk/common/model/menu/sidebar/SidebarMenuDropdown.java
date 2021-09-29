package com.senior.cyber.frmk.common.model.menu.sidebar;

import com.senior.cyber.frmk.common.model.Badge;
import org.apache.wicket.WicketRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SidebarMenuDropdown extends SidebarMenu {

    private final String icon;

    private final String text;

    private final Badge badge;

    private final List<SidebarMenu> children;

    public SidebarMenuDropdown(String icon, String text, Badge badge, List<SidebarMenu> children) {
        this.icon = icon;
        this.text = text;
        this.badge = badge;
        if (children == null || children.isEmpty()) {
            throw new WicketRuntimeException("children is empty or null");
        }
        this.children = new ArrayList<>(children.size());
        this.children.addAll(children);
        for (SidebarMenu c : this.children) {
            this.pages.addAll(c.pages);
        }
    }

    public SidebarMenuDropdown(String icon, String text, Badge badge, SidebarMenu child, SidebarMenu... children) {
        this.icon = icon;
        this.text = text;
        this.badge = badge;
        if (children == null || children.length == 0) {
            this.children = new ArrayList<>(1);
            this.children.add(child);
        } else {
            this.children = new ArrayList<>(children.length + 1);
            this.children.add(child);
            this.children.addAll(Arrays.asList(children));
        }
        for (SidebarMenu c : this.children) {
            this.pages.addAll(c.pages);
        }
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public Badge getBadge() {
        return badge;
    }

    public List<SidebarMenu> getChildren() {
        return children;
    }

}
