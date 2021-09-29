package com.senior.cyber.frmk.common.model;

import com.senior.cyber.frmk.common.model.menu.sidebar.HierarchyType;

import java.io.Serializable;

public class Theme implements Serializable {

    private AccentColor accentColor;

    private BrandColor brandColor;
    private boolean brandSmallText;

    private SidebarColor sidebarColor;
    private boolean sidebarFixed;
    private boolean sidebarCollapsible;
    private boolean sidebarLegacy;
    private boolean sidebarCompact;
    private boolean sidebarSmallText;
    private HierarchyType sidebarHierarchyType;

    private boolean navbarFixed;
    private boolean navbarSmallText;
    private NavbarTheme navbarTheme;
    private NavbarColor navbarColor;

    private boolean footerFixed;
    private boolean footerSmallText;

    public Theme() {
        this.accentColor = AccentColor.Blue;

        this.brandColor = BrandColor.Blue;
        this.brandSmallText = false;

        this.footerFixed = true;
        this.footerSmallText = false;

        this.navbarSmallText = false;
        this.navbarFixed = true;
        this.navbarTheme = NavbarTheme.Light;
        this.navbarColor = NavbarColor.Blue;

        this.sidebarColor = SidebarColor.BlueLight;
        this.sidebarFixed = true;
        this.sidebarCompact = false;
        this.sidebarLegacy = false;
        this.sidebarHierarchyType = HierarchyType.None;
        this.sidebarSmallText = false;
        this.sidebarCollapsible = false;
    }

    public boolean isSidebarFixed() {
        return sidebarFixed;
    }

    public void setSidebarFixed(boolean sidebarFixed) {
        this.sidebarFixed = sidebarFixed;
    }

    public boolean isSidebarCollapsible() {
        return sidebarCollapsible;
    }

    public void setSidebarCollapsible(boolean sidebarCollapsible) {
        this.sidebarCollapsible = sidebarCollapsible;
    }

    public boolean isSidebarLegacy() {
        return sidebarLegacy;
    }

    public void setSidebarLegacy(boolean sidebarLegacy) {
        this.sidebarLegacy = sidebarLegacy;
    }

    public boolean isSidebarCompact() {
        return sidebarCompact;
    }

    public void setSidebarCompact(boolean sidebarCompact) {
        this.sidebarCompact = sidebarCompact;
    }

    public boolean isSidebarSmallText() {
        return sidebarSmallText;
    }

    public void setSidebarSmallText(boolean sidebarSmallText) {
        this.sidebarSmallText = sidebarSmallText;
    }

    public HierarchyType getSidebarHierarchyType() {
        return sidebarHierarchyType;
    }

    public void setSidebarHierarchyType(HierarchyType sidebarHierarchyType) {
        this.sidebarHierarchyType = sidebarHierarchyType;
    }

    public AccentColor getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(AccentColor accentColor) {
        this.accentColor = accentColor;
    }

    public BrandColor getBrandColor() {
        return brandColor;
    }

    public void setBrandColor(BrandColor brandColor) {
        this.brandColor = brandColor;
    }

    public SidebarColor getSidebarColor() {
        return sidebarColor;
    }

    public void setSidebarColor(SidebarColor sidebarColor) {
        this.sidebarColor = sidebarColor;
    }

    public NavbarTheme getNavbarTheme() {
        return navbarTheme;
    }

    public void setNavbarTheme(NavbarTheme navbarTheme) {
        this.navbarTheme = navbarTheme;
    }

    public NavbarColor getNavbarColor() {
        return navbarColor;
    }

    public void setNavbarColor(NavbarColor navbarColor) {
        this.navbarColor = navbarColor;
    }

    public boolean isNavbarFixed() {
        return navbarFixed;
    }

    public void setNavbarFixed(boolean navbarFixed) {
        this.navbarFixed = navbarFixed;
    }

    public boolean isNavbarSmallText() {
        return navbarSmallText;
    }

    public void setNavbarSmallText(boolean navbarSmallText) {
        this.navbarSmallText = navbarSmallText;
    }

    public boolean isBrandSmallText() {
        return brandSmallText;
    }

    public void setBrandSmallText(boolean brandSmallText) {
        this.brandSmallText = brandSmallText;
    }

    public boolean isFooterFixed() {
        return footerFixed;
    }

    public void setFooterFixed(boolean footerFixed) {
        this.footerFixed = footerFixed;
    }

    public boolean isFooterSmallText() {
        return footerSmallText;
    }

    public void setFooterSmallText(boolean footerSmallText) {
        this.footerSmallText = footerSmallText;
    }

}
