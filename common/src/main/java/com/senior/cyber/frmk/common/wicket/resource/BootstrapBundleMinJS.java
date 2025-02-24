package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BootstrapBundleMinJS extends FileSystemResourceReference {

    public static final BootstrapBundleMinJS INSTANCE = new BootstrapBundleMinJS();

    public BootstrapBundleMinJS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.JS_BOOTSTRAP_4).getPath());
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }

}
