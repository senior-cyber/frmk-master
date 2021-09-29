package com.senior.cyber.frmk.common.model;

import com.senior.cyber.frmk.common.model.menu.left.TopLeftMenu;
import com.senior.cyber.frmk.common.model.menu.right.TopRightMenu;

import java.io.Serializable;
import java.util.List;

public class Navbar implements Serializable {

    private boolean searchable;

    private List<TopLeftMenu> left;

    private List<TopRightMenu> right;

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public List<TopLeftMenu> getLeft() {
        return left;
    }

    public void setLeft(List<TopLeftMenu> left) {
        this.left = left;
    }

    public List<TopRightMenu> getRight() {
        return right;
    }

    public void setRight(List<TopRightMenu> right) {
        this.right = right;
    }

}
