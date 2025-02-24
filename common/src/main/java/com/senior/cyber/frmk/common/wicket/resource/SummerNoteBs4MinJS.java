package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SummerNoteBs4MinJS extends FileSystemResourceReference {

    public static final SummerNoteBs4MinJS INSTANCE = new SummerNoteBs4MinJS();

    public SummerNoteBs4MinJS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.JS_SUMMER_NOTE).getPath());
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>();
        dependencies.add(CssHeaderItem.forReference(SummerNoteBs4CSS.INSTANCE));
        return dependencies;
    }
}
