package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class FontAwesomeCSS extends FileSystemResourceReference {

    public static FontAwesomeCSS INSTANCE = new FontAwesomeCSS();

    public FontAwesomeCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_FONT_AWESOME).getPath());
    }

}
