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

public class AdminLteMinCSS extends FileSystemResourceReference {

    public static final AdminLteMinCSS INSTANCE = new AdminLteMinCSS();

    public AdminLteMinCSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_THEME_STYLE).getPath());
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        // <!-- Font Awesome -->
//        dependencies.add(CssHeaderItem.forReference(FontAwesomeCSS.INSTANCE));
        // <!-- Ionicons -->
        dependencies.add(IOnIconsMinCSS.INSTANCE);
        // <!-- Google Font: Source Sans Pro -->
        dependencies.add(GoogleFontCSS.INSTANCE);
        return dependencies;
    }

}
