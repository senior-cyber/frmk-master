package com.senior.cyber.frmk.html.pages.examples;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import com.senior.cyber.frmk.html.DashboardV2Page;
import com.senior.cyber.frmk.html.DashboardV3Page;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class LoginPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new BookmarkablePageLink<>("index2.html_1", DashboardV2Page.class));
        WebMarkupContainer index3 = new WebMarkupContainer("index3.html_1");
        index3.add(AttributeModifier.replace("action", urlFor(DashboardV3Page.class, new PageParameters()).toString()));
        add(index3);
        add(new BookmarkablePageLink<>("forgot-password.html_1", ForgotPasswordPage.class));
        add(new BookmarkablePageLink<>("register.html_1", RegisterPage.class));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_FONT_AWESOME).getPath())));
        // <!-- Ionicons -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_ION_ICONS));
        // <!-- icheck bootstrap -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_ICHECK_BOOTSTRAP).getPath())));
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
