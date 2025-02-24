package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class OverlayScrollbarsMinCSS extends FileSystemResourceReference {

    public static final OverlayScrollbarsMinCSS INSTANCE = new OverlayScrollbarsMinCSS();

    public OverlayScrollbarsMinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_OVERLAY_SCROLL_BAR).getPath());
    }

}
