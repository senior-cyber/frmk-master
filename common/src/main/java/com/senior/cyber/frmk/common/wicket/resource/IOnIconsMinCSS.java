package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;

public class IOnIconsMinCSS extends CssUrlReferenceHeaderItem {

    public static final IOnIconsMinCSS INSTANCE = new IOnIconsMinCSS();

    public IOnIconsMinCSS() {
        super(AdminLTEResourceReference.CSS_ION_ICONS, null, null);
    }

}
