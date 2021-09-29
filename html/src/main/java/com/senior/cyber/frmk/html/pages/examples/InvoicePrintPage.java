package com.senior.cyber.frmk.html.pages.examples;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;

public class InvoicePrintPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Image("img_credit_visa", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_CREDIT_VI)));
        add(new Image("img_credit_mastercard", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_CREDIT_MA)));
        add(new Image("img_credit_american-express", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_CREDIT_AE)));
        add(new Image("img_credit_paypal2", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_CREDIT_P2)));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_FONT_AWESOME)));
        // <!-- Ionicons -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_ION_ICONS));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_THEME_STYLE)));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
    }

}
