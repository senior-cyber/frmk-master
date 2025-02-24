package com.senior.cyber.frmk.html.pages.layout;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import com.senior.cyber.frmk.html.DashboardV3Page;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class TopNavPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BookmarkablePageLink<Void> index3_1 = new BookmarkablePageLink<>("index3.html_1", DashboardV3Page.class);
        add(index3_1);
        BookmarkablePageLink<Void> index3_2 = new BookmarkablePageLink<>("index3.html_2", DashboardV3Page.class);
        add(index3_2);

        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();

        index3_1.add(new Image("AdminLTELogo.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_LOGO).getPath())));
        add(new Image("user1-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_1).getPath())));
        add(new Image("user3-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_3).getPath())));
        add(new Image("user8-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_8).getPath())));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_FONT_AWESOME).getPath())));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_THEME_STYLE).getPath())));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        // <!-- Bootstrap 4 -->
        response.render(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_BOOTSTRAP_4).getPath())));
        // <!-- AdminLTE App -->
        response.render(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_ADMINLTE_APP).getPath())));
    }

}
