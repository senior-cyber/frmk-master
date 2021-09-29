package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class BootstrapColorPickerMinJS extends AdminLTEResourceReference {

    public static final BootstrapColorPickerMinJS INSTANCE = new BootstrapColorPickerMinJS();

    public BootstrapColorPickerMinJS() {
        super(JS_COLOR_PICKER);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(BootstrapColorPickerMinCSS.INSTANCE));
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }
}
