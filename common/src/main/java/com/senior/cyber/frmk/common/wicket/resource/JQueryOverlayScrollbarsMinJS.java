package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class JQueryOverlayScrollbarsMinJS extends FileSystemResourceReference {

    public static final JQueryOverlayScrollbarsMinJS INSTANCE = new JQueryOverlayScrollbarsMinJS();

    public JQueryOverlayScrollbarsMinJS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.JS_OVERLAY_SCROLL_BAR).getPath());
    }

}
