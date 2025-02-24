package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InputMaskOnDomReady extends OnDomReadyHeaderItem {

    public InputMaskOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryInputMaskBundleMinJS.INSTANCE));
        return dependencies;
    }

}
