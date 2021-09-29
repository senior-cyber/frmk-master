package com.senior.cyber.frmk.html.pages.layout;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.html.DashboardV3Page;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class TopNavPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BookmarkablePageLink<Void> index3 = new BookmarkablePageLink<>("index3.html", DashboardV3Page.class);
        add(index3);
        BookmarkablePageLink<Void> index3_logo = new BookmarkablePageLink<>("index3.html_logo", DashboardV3Page.class);
        add(index3_logo);

        index3_logo.add(new Image("img_logo", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_LOGO)));
        add(new Image("img_user_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_1)));
        add(new Image("img_user_3", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_3)));
        add(new Image("img_user_8", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_8)));
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
