package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.ArrayList;
import java.util.List;

public class CalendarOnDomReady extends OnDomReadyHeaderItem {

    private static final ResourceReference CALENDAR_MAIN_MIN_CSS = new AdminLTEResourceReference(AdminLTEResourceReference.CSS_FULL_CALENDAR_MAIN);
    private static final ResourceReference CALENDAR_MAIN_MIN_JS = new AdminLTEResourceReference(AdminLTEResourceReference.JS_FULL_CALENDAR_MAIN);

    public CalendarOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(FontAwesomeCSS.INSTANCE));
        dependencies.add(IOnIconsMinCSS.INSTANCE);
        dependencies.add(GoogleFontCSS.INSTANCE);
        dependencies.add(CssHeaderItem.forReference(AdminLteMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(AdminLteMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryUIMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(MomentMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(BootstrapBundleMinJS.INSTANCE));
        dependencies.add(CssHeaderItem.forReference(CALENDAR_MAIN_MIN_CSS));
        dependencies.add(JavaScriptHeaderItem.forReference(CALENDAR_MAIN_MIN_JS));
        return dependencies;
    }

}
