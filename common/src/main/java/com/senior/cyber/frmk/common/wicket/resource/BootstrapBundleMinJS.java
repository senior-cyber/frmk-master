package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class BootstrapBundleMinJS extends AdminLTEResourceReference {

    public static final BootstrapBundleMinJS INSTANCE = new BootstrapBundleMinJS();

    public BootstrapBundleMinJS() {
        super(JS_BOOTSTRAP_4);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }

}
