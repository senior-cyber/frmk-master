package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class Select2FullMinJS extends AdminLTEResourceReference {

    public static final Select2FullMinJS INSTANCE = new Select2FullMinJS();

    public Select2FullMinJS() {
        super(JS_SELECT_2);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(JavaScriptHeaderItem.forReference(JQueryMinJS.INSTANCE));
        return dependencies;
    }

}
