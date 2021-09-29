package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.model.Theme;

import java.io.Serializable;

public interface IThemeProvider extends Serializable {

    Theme fetchTheme();

}
