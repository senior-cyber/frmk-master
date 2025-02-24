package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BootstrapColorPickerMinJS extends FileSystemResourceReference {

    public static final BootstrapColorPickerMinJS INSTANCE = new BootstrapColorPickerMinJS();

    public BootstrapColorPickerMinJS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.JS_COLOR_PICKER).getPath());
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(BootstrapColorPickerMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }
}
