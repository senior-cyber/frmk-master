package com.senior.cyber.frmk.html.pages.layout;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import com.senior.cyber.frmk.common.base.WebUiProperties;
import com.senior.cyber.frmk.html.*;
import com.senior.cyber.frmk.html.factory.WicketFactory;
import com.senior.cyber.frmk.html.factory.WicketFactory;
import com.senior.cyber.frmk.html.pages.chart.UPlotPage;
import com.senior.cyber.frmk.html.pages.examples.*;
import com.senior.cyber.frmk.html.pages.ui.*;
import com.senior.cyber.frmk.html.pages.CalendarPage;
import com.senior.cyber.frmk.html.pages.GalleryPage;
import com.senior.cyber.frmk.html.pages.KanbanPage;
import com.senior.cyber.frmk.html.pages.WidgetsPage;
import com.senior.cyber.frmk.html.pages.chart.ChartJSPage;
import com.senior.cyber.frmk.html.pages.chart.FlotPage;
import com.senior.cyber.frmk.html.pages.chart.InlinePage;
import com.senior.cyber.frmk.html.pages.forms.AdvancedPage;
import com.senior.cyber.frmk.html.pages.forms.EditorsPage;
import com.senior.cyber.frmk.html.pages.forms.ValidationPage;
import com.senior.cyber.frmk.html.pages.mailbox.ComposePage;
import com.senior.cyber.frmk.html.pages.mailbox.MailboxPage;
import com.senior.cyber.frmk.html.pages.mailbox.ReadMailPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedPage;
import com.senior.cyber.frmk.html.pages.tables.DataPage;
import com.senior.cyber.frmk.html.pages.tables.JSGridPage;
import com.senior.cyber.frmk.html.pages.tables.SimplePage;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TopNavSidebarPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BookmarkablePageLink<Void> index3_1 = new BookmarkablePageLink<>("index3.html_1", DashboardV3Page.class);
        add(index3_1);
        BookmarkablePageLink<Void> index3_2 = new BookmarkablePageLink<>("index3.html_2", DashboardV3Page.class);
        add(index3_2);
        BookmarkablePageLink<Void> index3_3 = new BookmarkablePageLink<>("index3.html_3", DashboardV3Page.class);
        add(index3_3);
        BookmarkablePageLink<Void> index3_4 = new BookmarkablePageLink<>("index3.html_4", DashboardV3Page.class);
        add(index3_4);


        add(new BookmarkablePageLink<>("index.html_1", DashboardV1Page.class));
        add(new BookmarkablePageLink<>("index2.html_1", DashboardV2Page.class));
        add(new BookmarkablePageLink<>("iframe.html_1", IFramePage.class));

        add(new BookmarkablePageLink<>("widgets.html_1", WidgetsPage.class));

        add(new BookmarkablePageLink<>("top-nav.html_1", TopNavPage.class));
        add(new BookmarkablePageLink<>("boxed.html_1", BoxedPage.class));
        add(new BookmarkablePageLink<>("fixed-sidebar.html_1", FixedSidebarPage.class));
        add(new BookmarkablePageLink<>("fixed-topnav.html_1", FixedTopNavPage.class));
        add(new BookmarkablePageLink<>("fixed-footer.html_1", FixedFooterPage.class));
        add(new BookmarkablePageLink<>("collapsed-sidebar.html_1", CollapsedSidebarPage.class));
        add(new BookmarkablePageLink<>("top-nav-sidebar.html_1", TopNavSidebarPage.class));
        add(new BookmarkablePageLink<>("fixed-sidebar-custom.html_1", FixedSidebarCustomPage.class));

        add(new BookmarkablePageLink<>("kanban.html_1", KanbanPage.class));
        add(new BookmarkablePageLink<>("simple.html_2", com.senior.cyber.frmk.html.pages.search.SimplePage.class));
        add(new BookmarkablePageLink<>("enhanced.html_1", EnhancedPage.class));

        add(new BookmarkablePageLink<>("chartjs.html_1", ChartJSPage.class));
        add(new BookmarkablePageLink<>("flot.html_1", FlotPage.class));
        add(new BookmarkablePageLink<>("inline.html_1", InlinePage.class));
        add(new BookmarkablePageLink<>("uplot.html_1", UPlotPage.class));

        add(new BookmarkablePageLink<>("general.html_1", com.senior.cyber.frmk.html.pages.ui.GeneralPage.class));
        add(new BookmarkablePageLink<>("icons.html_1", IconsPage.class));
        add(new BookmarkablePageLink<>("buttons.html_1", ButtonsPage.class));
        add(new BookmarkablePageLink<>("sliders.html_1", SlidersPage.class));
        add(new BookmarkablePageLink<>("modals.html_1", ModalsPage.class));
        add(new BookmarkablePageLink<>("navbar.html_1", NavbarPage.class));
        add(new BookmarkablePageLink<>("timeline.html_1", TimelinePage.class));
        add(new BookmarkablePageLink<>("ribbons.html_1", RibbonsPage.class));

        add(new BookmarkablePageLink<>("general.html_2", com.senior.cyber.frmk.html.pages.forms.GeneralPage.class));
        add(new BookmarkablePageLink<>("advanced.html_1", AdvancedPage.class));
        add(new BookmarkablePageLink<>("editors.html_1", EditorsPage.class));
        add(new BookmarkablePageLink<>("validation.html_1", ValidationPage.class));

        add(new BookmarkablePageLink<>("simple.html_1", SimplePage.class));
        add(new BookmarkablePageLink<>("data.html_1", DataPage.class));
        add(new BookmarkablePageLink<>("jsgrid.html_1", JSGridPage.class));

        add(new BookmarkablePageLink<>("calendar.html_1", CalendarPage.class));
        add(new BookmarkablePageLink<>("gallery.html_1", GalleryPage.class));

        add(new BookmarkablePageLink<>("mailbox.html_1", MailboxPage.class));
        add(new BookmarkablePageLink<>("compose.html_1", ComposePage.class));
        add(new BookmarkablePageLink<>("read-mail.html_1", ReadMailPage.class));

        add(new BookmarkablePageLink<>("faq.html_1", FaqPage.class));
        add(new BookmarkablePageLink<>("contact-us.html_1", ContactUsPage.class));
        add(new BookmarkablePageLink<>("login-v2.html_1", LoginV2Page.class));
        add(new BookmarkablePageLink<>("register-v2.html_1", RegisterV2Page.class));
        add(new BookmarkablePageLink<>("forgot-password-v2.html_1", ForgotPasswordV2Page.class));
        add(new BookmarkablePageLink<>("recover-password-v2.html_1", RecoverPasswordV2Page.class));
        add(new BookmarkablePageLink<>("invoice.html_1", InvoicePage.class));
        add(new BookmarkablePageLink<>("profile.html_1", ProfilePage.class));
        add(new BookmarkablePageLink<>("e-commerce.html_1", ECommercePage.class));
        add(new BookmarkablePageLink<>("projects.html_1", ProjectsPage.class));
        add(new BookmarkablePageLink<>("project-add.html_1", ProjectAddPage.class));
        add(new BookmarkablePageLink<>("project-edit.html_1", ProjectEditPage.class));
        add(new BookmarkablePageLink<>("project-detail.html_1", ProjectDetailPage.class));
        add(new BookmarkablePageLink<>("contacts.html_1", ContactsPage.class));
        add(new BookmarkablePageLink<>("login.html_1", LoginPage.class));
        add(new BookmarkablePageLink<>("register.html_1", RegisterPage.class));
        add(new BookmarkablePageLink<>("forgot-password.html_1", ForgotPasswordPage.class));
        add(new BookmarkablePageLink<>("recover-password.html_1", RecoverPasswordPage.class));
        add(new BookmarkablePageLink<>("lockscreen.html_1", LockScreenPage.class));
        add(new BookmarkablePageLink<>("legacy-user-menu.html_1", LegacyUserMenuPage.class));
        add(new BookmarkablePageLink<>("language-menu.html_1", LanguageMenuPage.class));
        add(new BookmarkablePageLink<>("404.html_1", C404Page.class));
        add(new BookmarkablePageLink<>("500.html_1", C500Page.class));
        add(new BookmarkablePageLink<>("pace.html_1", PacePage.class));
        add(new BookmarkablePageLink<>("blank.html_1", BlankPage.class));

        add(new BookmarkablePageLink<>("starter.html_1", StarterPage.class));

        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();

        index3_1.add(new Image("AdminLTELogo.png_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_LOGO).getPath())));
        index3_3.add(new Image("AdminLTELogo.png_2", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_LOGO).getPath())));

        add(new Image("user1-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_1).getPath())));
        add(new Image("user2-160x160.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_2).getPath())));
        add(new Image("user3-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_3).getPath())));
        add(new Image("user8-128x128.jpg_1", new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.IMG_USER_8).getPath())));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_FONT_AWESOME).getPath())));
        // <!-- Ionicons -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_ION_ICONS));
        // <!-- overlayScrollbars -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_OVERLAY_SCROLL_BAR).getPath())));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.CSS_THEME_STYLE).getPath())));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        // <!-- Bootstrap -->
        response.render(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_BOOTSTRAP_4).getPath())));
        // <!-- overlayScrollbars -->
        response.render(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_OVERLAY_SCROLL_BAR).getPath())));
        // <!-- AdminLTE App -->
        response.render(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_ADMINLTE_APP).getPath())));
        try {
            ApplicationContext context = WicketFactory.getApplicationContext();
            WebUiProperties properties = context.getBean(WebUiProperties.class);
            response.render(OnDomReadyHeaderItem.forScript(FileUtils.readFileToString(new File(properties.getAdminLte(), AdminLTEResourceReference.JS_DIST_DEMO), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
