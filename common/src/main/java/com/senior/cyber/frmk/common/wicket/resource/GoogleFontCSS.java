package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;

public class GoogleFontCSS extends CssUrlReferenceHeaderItem {

    public static final GoogleFontCSS INSTANCE = new GoogleFontCSS();

    public GoogleFontCSS() {
        super(AdminLTEResourceReference.CSS_GOOGLE_FONT, null, null);
    }

}
