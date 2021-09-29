package com.senior.cyber.frmk.common.model.menu.sidebar;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class SidebarMenu implements Serializable {

    protected Set<String> pages = new TreeSet<>();

    public SidebarMenu() {
    }

    public Set<String> getPages() {
        return pages;
    }

}
