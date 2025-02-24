package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class ICheckBootstrapMinCSS extends FileSystemResourceReference {

    public static final ICheckBootstrapMinCSS INSTANCE = new ICheckBootstrapMinCSS();

    public ICheckBootstrapMinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_ICHECK_BOOTSTRAP).getPath());
    }

}
