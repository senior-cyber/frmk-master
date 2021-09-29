package com.senior.cyber.frmk.html.pages.examples;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.WebUiProperties;
import com.senior.cyber.frmk.html.*;
import com.senior.cyber.frmk.html.factory.WicketFactory;
import com.senior.cyber.frmk.html.pages.CalendarPage;
import com.senior.cyber.frmk.html.pages.GalleryPage;
import com.senior.cyber.frmk.html.pages.KanbanPage;
import com.senior.cyber.frmk.html.pages.WidgetsPage;
import com.senior.cyber.frmk.html.pages.chart.FlotPage;
import com.senior.cyber.frmk.html.pages.chart.InlinePage;
import com.senior.cyber.frmk.html.pages.chart.UPlotPage;
import com.senior.cyber.frmk.html.pages.forms.AdvancedPage;
import com.senior.cyber.frmk.html.pages.forms.ValidationPage;
import com.senior.cyber.frmk.html.pages.layout.*;
import com.senior.cyber.frmk.html.pages.mailbox.ComposePage;
import com.senior.cyber.frmk.html.pages.mailbox.MailboxPage;
import com.senior.cyber.frmk.html.pages.mailbox.ReadMailPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedPage;
import com.senior.cyber.frmk.html.pages.tables.DataPage;
import com.senior.cyber.frmk.html.pages.ui.*;
import com.senior.cyber.frmk.html.pages.chart.ChartJSPage;
import com.senior.cyber.frmk.html.pages.forms.EditorsPage;
import com.senior.cyber.frmk.html.pages.forms.GeneralPage;
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
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacePage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BookmarkablePageLink<Void> index3_1 = new BookmarkablePageLink<>("index3.html_1", DashboardV3Page.class);
        add(index3_1);
        BookmarkablePageLink<Void> index3_2 = new BookmarkablePageLink<>("index3.html_2", DashboardV3Page.class);
        add(index3_2);
        BookmarkablePageLink<Void> index3_logo = new BookmarkablePageLink<>("index3.html_logo", DashboardV3Page.class);
        add(index3_logo);

        add(new BookmarkablePageLink<>("index.html", DashboardV1Page.class));
        add(new BookmarkablePageLink<>("index2.html", DashboardV2Page.class));
        add(new BookmarkablePageLink<>("iframe.html", IFramePage.class));

        add(new BookmarkablePageLink<>("pages/widgets.html", WidgetsPage.class));

        add(new BookmarkablePageLink<>("pages/kanban.html", KanbanPage.class));
        add(new BookmarkablePageLink<>("pages/search/simple.html", com.senior.cyber.frmk.html.pages.search.SimplePage.class));
        add(new BookmarkablePageLink<>("pages/search/enhanced.html", EnhancedPage.class));

        add(new BookmarkablePageLink<>("pages/layout/top-nav.html", TopNavPage.class));
        add(new BookmarkablePageLink<>("pages/layout/boxed.html", BoxedPage.class));
        add(new BookmarkablePageLink<>("pages/layout/fixed-sidebar.html", FixedSidebarPage.class));
        add(new BookmarkablePageLink<>("pages/layout/fixed-sidebar-custom.html", FixedSidebarCustomPage.class));
        add(new BookmarkablePageLink<>("pages/layout/fixed-topnav.html", FixedTopNavPage.class));
        add(new BookmarkablePageLink<>("pages/layout/fixed-footer.html", FixedFooterPage.class));
        add(new BookmarkablePageLink<>("pages/layout/collapsed-sidebar.html", CollapsedSidebarPage.class));
        add(new BookmarkablePageLink<>("pages/layout/top-nav-sidebar.html", TopNavSidebarPage.class));

        add(new BookmarkablePageLink<>("pages/charts/chartjs.html", ChartJSPage.class));
        add(new BookmarkablePageLink<>("pages/charts/flot.html", FlotPage.class));
        add(new BookmarkablePageLink<>("pages/charts/inline.html", InlinePage.class));
        add(new BookmarkablePageLink<>("pages/charts/uplot.html", UPlotPage.class));

        add(new BookmarkablePageLink<>("pages/UI/general.html", com.senior.cyber.frmk.html.pages.ui.GeneralPage.class));
        add(new BookmarkablePageLink<>("pages/UI/icons.html", IconsPage.class));
        add(new BookmarkablePageLink<>("pages/UI/buttons.html", ButtonsPage.class));
        add(new BookmarkablePageLink<>("pages/UI/sliders.html", SlidersPage.class));
        add(new BookmarkablePageLink<>("pages/UI/modals.html", ModalsPage.class));
        add(new BookmarkablePageLink<>("pages/UI/navbar.html", NavbarPage.class));
        add(new BookmarkablePageLink<>("pages/UI/timeline.html", TimelinePage.class));
        add(new BookmarkablePageLink<>("pages/UI/ribbons.html", RibbonsPage.class));

        add(new BookmarkablePageLink<>("pages/forms/general.html", GeneralPage.class));
        add(new BookmarkablePageLink<>("pages/forms/advanced.html", AdvancedPage.class));
        add(new BookmarkablePageLink<>("pages/forms/editors.html", EditorsPage.class));
        add(new BookmarkablePageLink<>("pages/forms/validation.html", ValidationPage.class));

        add(new BookmarkablePageLink<>("pages/tables/simple.html", SimplePage.class));
        add(new BookmarkablePageLink<>("pages/tables/data.html", DataPage.class));
        add(new BookmarkablePageLink<>("pages/tables/jsgrid.html", JSGridPage.class));

        add(new BookmarkablePageLink<>("pages/calendar.html", CalendarPage.class));
        add(new BookmarkablePageLink<>("pages/gallery.html", GalleryPage.class));

        add(new BookmarkablePageLink<>("pages/mailbox/mailbox.html", MailboxPage.class));
        add(new BookmarkablePageLink<>("pages/mailbox/compose.html", ComposePage.class));
        add(new BookmarkablePageLink<>("pages/mailbox/read-mail.html", ReadMailPage.class));

        add(new BookmarkablePageLink<>("pages/examples/faq.html", FaqPage.class));
        add(new BookmarkablePageLink<>("pages/examples/contact-us.html", ContactUsPage.class));
        add(new BookmarkablePageLink<>("pages/examples/login-v2.html", LoginV2Page.class));
        add(new BookmarkablePageLink<>("pages/examples/register-v2.html", RegisterV2Page.class));
        add(new BookmarkablePageLink<>("pages/examples/forgot-password-v2.html", ForgotPasswordV2Page.class));
        add(new BookmarkablePageLink<>("pages/examples/recover-password-v2.html", RecoverPasswordV2Page.class));
        add(new BookmarkablePageLink<>("pages/examples/invoice.html", InvoicePage.class));
        add(new BookmarkablePageLink<>("pages/examples/profile.html", ProfilePage.class));
        add(new BookmarkablePageLink<>("pages/examples/e-commerce.html", ECommercePage.class));
        add(new BookmarkablePageLink<>("pages/examples/projects.html", ProjectsPage.class));
        add(new BookmarkablePageLink<>("pages/examples/project-add.html", ProjectAddPage.class));
        add(new BookmarkablePageLink<>("pages/examples/project-edit.html", ProjectEditPage.class));
        add(new BookmarkablePageLink<>("pages/examples/project-detail.html", ProjectDetailPage.class));
        add(new BookmarkablePageLink<>("pages/examples/contacts.html", ContactsPage.class));
        add(new BookmarkablePageLink<>("pages/examples/login.html", LoginPage.class));
        add(new BookmarkablePageLink<>("pages/examples/register.html", RegisterPage.class));
        add(new BookmarkablePageLink<>("pages/examples/forgot-password.html", ForgotPasswordPage.class));
        add(new BookmarkablePageLink<>("pages/examples/recover-password.html", RecoverPasswordPage.class));
        add(new BookmarkablePageLink<>("pages/examples/lockscreen.html", LockScreenPage.class));
        add(new BookmarkablePageLink<>("pages/examples/legacy-user-menu.html", LegacyUserMenuPage.class));
        add(new BookmarkablePageLink<>("pages/examples/language-menu.html", LanguageMenuPage.class));
        add(new BookmarkablePageLink<>("pages/examples/404.html", C404Page.class));
        add(new BookmarkablePageLink<>("pages/examples/500.html", C500Page.class));
        add(new BookmarkablePageLink<>("pages/examples/pace.html", PacePage.class));
        add(new BookmarkablePageLink<>("pages/examples/blank.html", BlankPage.class));

        add(new BookmarkablePageLink<>("starter.html", StarterPage.class));

        index3_logo.add(new Image("img_logo", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_LOGO)));
        add(new Image("img_user_1", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_1)));
        add(new Image("img_user_2", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_2)));
        add(new Image("img_user_3", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_3)));
        add(new Image("img_user_8", new AdminLTEResourceReference(AdminLTEResourceReference.IMG_USER_8)));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // <!-- Font Awesome -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_FONT_AWESOME)));
        // <!-- Ionicons -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_ION_ICONS));
        //  <!-- pace-progress -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_PACE_THEME_FLAT_TOP)));
        // <!-- Theme style -->
        response.render(CssHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.CSS_THEME_STYLE)));
        // <!-- Google Font: Source Sans Pro -->
        response.render(CssHeaderItem.forUrl(AdminLTEResourceReference.CSS_GOOGLE_FONT));

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        // <!-- Bootstrap 4 -->
        response.render(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_BOOTSTRAP_4)));
        // <!-- pace-progress -->
        response.render(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_PACE)));
        // <!-- AdminLTE App -->
        response.render(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference(AdminLTEResourceReference.JS_ADMINLTE_APP)));
        try {
            ApplicationContext context = WicketFactory.getApplicationContext();
            WebUiProperties properties = context.getBean(WebUiProperties.class);
            response.render(OnDomReadyHeaderItem.forScript(FileUtils.readFileToString(new File(properties.getAdminLTE(), AdminLTEResourceReference.JS_DIST_DEMO), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
