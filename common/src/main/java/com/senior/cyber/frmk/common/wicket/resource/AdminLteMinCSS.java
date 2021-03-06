package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;

import java.util.ArrayList;
import java.util.List;

public class AdminLteMinCSS extends AdminLTEResourceReference {

    public static final AdminLteMinCSS INSTANCE = new AdminLteMinCSS();

    public AdminLteMinCSS() {
        super(CSS_THEME_STYLE);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        // <!-- Font Awesome -->
        dependencies.add(CssHeaderItem.forReference(FontAwesomeCSS.INSTANCE));
        // <!-- Ionicons -->
        dependencies.add(IOnIconsMinCSS.INSTANCE);
        // <!-- Google Font: Source Sans Pro -->
        dependencies.add(GoogleFontCSS.INSTANCE);
        return dependencies;
    }

}
