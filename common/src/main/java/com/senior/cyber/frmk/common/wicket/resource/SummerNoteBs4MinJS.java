package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;

import java.util.ArrayList;
import java.util.List;

public class SummerNoteBs4MinJS extends AdminLTEResourceReference {

    public static final SummerNoteBs4MinJS INSTANCE = new SummerNoteBs4MinJS();

    public SummerNoteBs4MinJS() {
        super(JS_SUMMER_NOTE);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>();
        dependencies.add(CssHeaderItem.forReference(SummerNoteBs4CSS.INSTANCE));
        return dependencies;
    }
}
