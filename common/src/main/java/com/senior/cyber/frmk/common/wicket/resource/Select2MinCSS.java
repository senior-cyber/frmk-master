package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class Select2MinCSS extends FileSystemResourceReference {

    public static final Select2MinCSS INSTANCE = new Select2MinCSS();

    public Select2MinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_SELECT_2).getPath());
    }

}
