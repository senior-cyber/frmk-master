package com.senior.cyber.frmk.html.pages.examples;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class InvoicePrintPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        add(new Image("visa.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_CREDIT_VI).getPath())));
        add(new Image("mastercard.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_CREDIT_MA).getPath())));
        add(new Image("american-express.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_CREDIT_AE).getPath())));
        add(new Image("paypal2.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_CREDIT_P2).getPath())));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_FONT_AWESOME).getPath())));
        // <!-- Ionicons -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_ION_ICONS));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_THEME_STYLE).getPath())));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
    }

}
