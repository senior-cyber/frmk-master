package com.senior.cyber.frmk.common.wicket.resource;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class RichTextOnDomReady extends OnDomReadyHeaderItem {

    public RichTextOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(IOnIconsMinCSS.INSTANCE);
        dependencies.add(CssHeaderItem.forReference(AdminLteMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(BootstrapBundleMinJS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(SummerNoteBs4MinJS.INSTANCE));
        return dependencies;
    }

}
