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

public class JQueryInputMaskBundleMinJS extends FileSystemResourceReference {

    public static final JQueryInputMaskBundleMinJS INSTANCE = new JQueryInputMaskBundleMinJS();

    public JQueryInputMaskBundleMinJS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.JS_INPUT_MASK).getPath());
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }

}
