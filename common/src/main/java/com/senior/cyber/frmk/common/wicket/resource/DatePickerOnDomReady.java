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

public class DatePickerOnDomReady extends OnDomReadyHeaderItem {

    public DatePickerOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_MOMENT).getPath())));
        dependencies.add(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_TEMPUSDOMINUS_BOOTSTRAP).getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_MOMENT).getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_TEMPUSDOMINUS_BOOTSTRAP).getPath())));
        return dependencies;
    }

}
