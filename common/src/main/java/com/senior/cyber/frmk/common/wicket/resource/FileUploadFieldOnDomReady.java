package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUploadFieldOnDomReady extends OnDomReadyHeaderItem {

    public FileUploadFieldOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(AdminLteMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(BootstrapBundleMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_FILE_UPLOAD).getPath())));
        return dependencies;
    }

}
