package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class FileUploadFieldOnDomReady extends OnDomReadyHeaderItem {

    public FileUploadFieldOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(AdminLteMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(BootstrapBundleMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_FILE_UPLOAD)));
        return dependencies;
    }

}
