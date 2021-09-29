package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class Select2OnDomReady extends OnDomReadyHeaderItem {

    public Select2OnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(Select2MinCSS.INSTANCE));
        dependencies.add(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_SELECT_2_BOOTSTRAP)));
        dependencies.add(JavaScriptHeaderItem.forReference(Select2FullMinJS.INSTANCE));
        return dependencies;
    }

}
