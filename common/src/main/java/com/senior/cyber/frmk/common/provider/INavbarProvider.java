package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.model.Navbar;

import java.io.Serializable;

public interface INavbarProvider extends Serializable {

    Navbar fetchNavbar();

}
