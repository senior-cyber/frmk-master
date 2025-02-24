package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class BootstrapColorPickerMinCSS extends FileSystemResourceReference {

    public static final BootstrapColorPickerMinCSS INSTANCE = new BootstrapColorPickerMinCSS();

    public BootstrapColorPickerMinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_COLOR_PICKER).getPath());
    }

}
