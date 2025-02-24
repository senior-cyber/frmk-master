package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class Select2Bootstrap4MinCSS extends FileSystemResourceReference {

    public static final Select2Bootstrap4MinCSS INSTANCE = new Select2Bootstrap4MinCSS();

    public Select2Bootstrap4MinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_SELECT_2).getPath());
    }

}
