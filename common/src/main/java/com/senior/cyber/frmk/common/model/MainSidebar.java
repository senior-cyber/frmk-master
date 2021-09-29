package com.senior.cyber.frmk.common.model;

import com.senior.cyber.frmk.common.model.menu.sidebar.SidebarMenu;

import java.io.Serializable;
import java.util.List;

public class MainSidebar implements Serializable {

    private Brand brand;

    private UserPanel userPanel;

    private List<SidebarMenu> sidebarMenu;

    private boolean searchable;

    public List<SidebarMenu> getSidebarMenu() {
        return sidebarMenu;
    }

    public void setSidebarMenu(List<SidebarMenu> sidebarMenu) {
        this.sidebarMenu = sidebarMenu;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public void setUserPanel(UserPanel userPanel) {
        this.userPanel = userPanel;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

}
