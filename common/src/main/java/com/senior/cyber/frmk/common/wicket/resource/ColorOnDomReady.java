package com.senior.cyber.frmk.common.wicket.resource;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class ColorOnDomReady extends OnDomReadyHeaderItem {

    public ColorOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(BootstrapColorPickerMinJS.INSTANCE));
        return dependencies;
    }

}
