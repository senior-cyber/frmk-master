package com.senior.cyber.frmk.html.factory;

import com.senior.cyber.frmk.common.base.WebUiProperties;
import com.senior.cyber.frmk.html.*;
import com.senior.cyber.frmk.html.pages.chart.UPlotPage;
import com.senior.cyber.frmk.html.pages.examples.*;
import com.senior.cyber.frmk.html.pages.layout.*;
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
import com.senior.cyber.frmk.html.pages.forms.GeneralPage;
import com.senior.cyber.frmk.html.pages.forms.ValidationPage;
import com.senior.cyber.frmk.html.pages.mailbox.ComposePage;
import com.senior.cyber.frmk.html.pages.mailbox.MailboxPage;
import com.senior.cyber.frmk.html.pages.mailbox.ReadMailPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedResultsPage;
import com.senior.cyber.frmk.html.pages.search.SimpleResultsPage;
import com.senior.cyber.frmk.html.pages.tables.DataPage;
import com.senior.cyber.frmk.html.pages.tables.JSGridPage;
import com.senior.cyber.frmk.html.pages.tables.SimplePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.*;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.FileSystemResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class WicketApplication extends WebApplication implements IResourceFinder {

    private static final Map<String, String> PAGES = new HashMap<>();

    static {
        PAGES.put(ChartJSPage.class.getName(), "/pages/charts/chartjs.html");
        PAGES.put(FlotPage.class.getName(), "/pages/charts/flot.html");
        PAGES.put(InlinePage.class.getName(), "/pages/charts/inline.html");
        PAGES.put(UPlotPage.class.getName(), "/pages/charts/uplot.html");

        PAGES.put(BlankPage.class.getName(), "/pages/examples/blank.html");
        PAGES.put(FaqPage.class.getName(), "/pages/examples/faq.html");
        PAGES.put(ContactUsPage.class.getName(), "/pages/examples/contact-us.html");
        PAGES.put(C404Page.class.getName(), "/pages/examples/404.html");
        PAGES.put(C500Page.class.getName(), "/pages/examples/500.html");
        PAGES.put(ContactsPage.class.getName(), "/pages/examples/contacts.html");
        PAGES.put(ECommercePage.class.getName(), "/pages/examples/e-commerce.html");
        PAGES.put(ForgotPasswordPage.class.getName(), "/pages/examples/forgot-password.html");
        PAGES.put(ForgotPasswordV2Page.class.getName(), "/pages/examples/forgot-password-v2.html");
        PAGES.put(InvoicePage.class.getName(), "/pages/examples/invoice.html");
        PAGES.put(InvoicePrintPage.class.getName(), "/pages/examples/invoice-print.html");
        PAGES.put(LanguageMenuPage.class.getName(), "/pages/examples/language-menu.html");
        PAGES.put(LegacyUserMenuPage.class.getName(), "/pages/examples/legacy-user-menu.html");
        PAGES.put(LockScreenPage.class.getName(), "/pages/examples/lockscreen.html");
        PAGES.put(LoginPage.class.getName(), "/pages/examples/login.html");
        PAGES.put(LoginV2Page.class.getName(), "/pages/examples/login-v2.html");
        PAGES.put(PacePage.class.getName(), "/pages/examples/pace.html");
        PAGES.put(ProfilePage.class.getName(), "/pages/examples/profile.html");
        PAGES.put(ProjectAddPage.class.getName(), "/pages/examples/project-add.html");
        PAGES.put(ProjectDetailPage.class.getName(), "/pages/examples/project-detail.html");
        PAGES.put(ProjectEditPage.class.getName(), "/pages/examples/project-edit.html");
        PAGES.put(ProjectsPage.class.getName(), "/pages/examples/projects.html");
        PAGES.put(RecoverPasswordPage.class.getName(), "/pages/examples/recover-password.html");
        PAGES.put(RecoverPasswordV2Page.class.getName(), "/pages/examples/recover-password-v2.html");
        PAGES.put(RegisterPage.class.getName(), "/pages/examples/register.html");
        PAGES.put(RegisterV2Page.class.getName(), "/pages/examples/register-v2.html");

        PAGES.put(KanbanPage.class.getName(), "/pages/kanban.html");
        PAGES.put(com.senior.cyber.frmk.html.pages.search.SimplePage.class.getName(), "/pages/search/simple.html");
        PAGES.put(SimpleResultsPage.class.getName(), "/pages/search/simple-results.html");
        PAGES.put(EnhancedPage.class.getName(), "/pages/search/enhanced.html");
        PAGES.put(EnhancedResultsPage.class.getName(), "/pages/search/enhanced-results.html");

        PAGES.put(AdvancedPage.class.getName(), "/pages/forms/advanced.html");
        PAGES.put(EditorsPage.class.getName(), "/pages/forms/editors.html");
        PAGES.put(GeneralPage.class.getName(), "/pages/forms/general.html");
        PAGES.put(ValidationPage.class.getName(), "/pages/forms/validation.html");

        PAGES.put(TopNavPage.class.getName(), "/pages/layout/top-nav.html");
        PAGES.put(BoxedPage.class.getName(), "/pages/layout/boxed.html");
        PAGES.put(FixedSidebarPage.class.getName(), "/pages/layout/fixed-sidebar.html");
        PAGES.put(FixedSidebarCustomPage.class.getName(), "/pages/layout/fixed-sidebar-custom.html");
        PAGES.put(FixedTopNavPage.class.getName(), "/pages/layout/fixed-topnav.html");
        PAGES.put(FixedFooterPage.class.getName(), "/pages/layout/fixed-footer.html");
        PAGES.put(CollapsedSidebarPage.class.getName(), "/pages/layout/collapsed-sidebar.html");
        PAGES.put(TopNavSidebarPage.class.getName(), "/pages/layout/top-nav-sidebar.html");

        PAGES.put(ComposePage.class.getName(), "/pages/mailbox/compose.html");
        PAGES.put(MailboxPage.class.getName(), "/pages/mailbox/mailbox.html");
        PAGES.put(ReadMailPage.class.getName(), "/pages/mailbox/read-mail.html");

        PAGES.put(DataPage.class.getName(), "/pages/tables/data.html");
        PAGES.put(JSGridPage.class.getName(), "/pages/tables/jsgrid.html");
        PAGES.put(SimplePage.class.getName(), "/pages/tables/simple.html");

        PAGES.put(ButtonsPage.class.getName(), "/pages/UI/buttons.html");
        PAGES.put(com.senior.cyber.frmk.html.pages.ui.GeneralPage.class.getName(), "/pages/UI/general.html");
        PAGES.put(IconsPage.class.getName(), "/pages/UI/icons.html");
        PAGES.put(SlidersPage.class.getName(), "/pages/UI/sliders.html");
        PAGES.put(ModalsPage.class.getName(), "/pages/UI/modals.html");
        PAGES.put(NavbarPage.class.getName(), "/pages/UI/navbar.html");
        PAGES.put(RibbonsPage.class.getName(), "/pages/UI/ribbons.html");
        PAGES.put(TimelinePage.class.getName(), "/pages/UI/timeline.html");

        PAGES.put(WidgetsPage.class.getName(), "/pages/widgets.html");
        PAGES.put(CalendarPage.class.getName(), "/pages/calendar.html");
        PAGES.put(GalleryPage.class.getName(), "/pages/gallery.html");

        PAGES.put(DashboardV1Page.class.getName(), "/index.html");
        PAGES.put(DashboardV2Page.class.getName(), "/index2.html");
        PAGES.put(DashboardV3Page.class.getName(), "/index3.html");
        PAGES.put(StarterPage.class.getName(), "/starter.html");
        PAGES.put(IFramePage.class.getName(), "/iframe.html");
    }

    @Override
    protected void init() {
        super.init();
        getResourceSettings().getResourceFinders().add(0, this);
        for (Map.Entry<String, String> page : PAGES.entrySet()) {
            try {
                mountPage(page.getValue(), (Class<Page>) Class.forName(page.getKey()));
            } catch (ClassNotFoundException e) {
            }
        }
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return LoginPage.class;
    }

    @Override
    public IResourceStream find(Class<?> clazz, String pathname) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        WebUiProperties properties = context.getBean(WebUiProperties.class);
        if (PAGES.containsKey(clazz.getName())) {
            File html = new File(properties.getAdminLte(), PAGES.get(clazz.getName()));
            return new FileSystemResourceStream(html);
        } else {
            return null;
        }
    }

}
