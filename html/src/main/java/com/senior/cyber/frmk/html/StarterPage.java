package com.senior.cyber.frmk.html;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class StarterPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BookmarkablePageLink<Void> index3_1 = new BookmarkablePageLink<>("index3.html_1", DashboardV3Page.class);
        add(index3_1);
        BookmarkablePageLink<Void> index3_2 = new BookmarkablePageLink<>("index3.html_2", DashboardV3Page.class);
        add(index3_2);

        index3_2.add(new Image("AdminLTELogo.png_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_LOGO)));
        add(new Image("user1-128x128.jpg_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_1)));
        add(new Image("user2-160x160.jpg_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_2)));
        add(new Image("user3-128x128.jpg_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_3)));
        add(new Image("user8-128x128.jpg_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_8)));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_FONT_AWESOME)));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_THEME_STYLE)));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        // <!-- Bootstrap 4 -->
        response.render(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_BOOTSTRAP_4)));
        // <!-- AdminLTE App -->
        response.render(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_ADMINLTE_APP)));
    }
}
