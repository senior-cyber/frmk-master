package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class DatePickerOnDomReady extends OnDomReadyHeaderItem {

    public DatePickerOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_MOMENT)));
        dependencies.add(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_TEMPUSDOMINUS_BOOTSTRAP)));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_MOMENT)));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_TEMPUSDOMINUS_BOOTSTRAP)));
        return dependencies;
    }

}
