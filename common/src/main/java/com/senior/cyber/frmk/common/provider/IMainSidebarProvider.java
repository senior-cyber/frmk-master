package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.model.MainSidebar;

import java.io.Serializable;

public interface IMainSidebarProvider extends Serializable {

    MainSidebar fetchMainSidebar();

}
