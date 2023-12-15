package com.senior.cyber.frmk.html.factory;

import com.senior.cyber.frmk.common.base.AbstractWicketFactory;
import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.WebUiProperties;
import com.senior.cyber.frmk.html.*;
import com.senior.cyber.frmk.html.pages.CalendarPage;
import com.senior.cyber.frmk.html.pages.GalleryPage;
import com.senior.cyber.frmk.html.pages.KanbanPage;
import com.senior.cyber.frmk.html.pages.WidgetsPage;
import com.senior.cyber.frmk.html.pages.chart.ChartJSPage;
import com.senior.cyber.frmk.html.pages.chart.FlotPage;
import com.senior.cyber.frmk.html.pages.chart.InlinePage;
import com.senior.cyber.frmk.html.pages.chart.UPlotPage;
import com.senior.cyber.frmk.html.pages.examples.*;
import com.senior.cyber.frmk.html.pages.forms.AdvancedPage;
import com.senior.cyber.frmk.html.pages.forms.EditorsPage;
import com.senior.cyber.frmk.html.pages.forms.GeneralPage;
import com.senior.cyber.frmk.html.pages.forms.ValidationPage;
import com.senior.cyber.frmk.html.pages.layout.*;
import com.senior.cyber.frmk.html.pages.mailbox.ComposePage;
import com.senior.cyber.frmk.html.pages.mailbox.MailboxPage;
import com.senior.cyber.frmk.html.pages.mailbox.ReadMailPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedPage;
import com.senior.cyber.frmk.html.pages.search.EnhancedResultsPage;
import com.senior.cyber.frmk.html.pages.search.SimpleResultsPage;
import com.senior.cyber.frmk.html.pages.tables.DataPage;
import com.senior.cyber.frmk.html.pages.tables.JSGridPage;
import com.senior.cyber.frmk.html.pages.tables.SimplePage;
import com.senior.cyber.frmk.html.pages.ui.*;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.request.resource.*;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.FileSystemResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class WicketFactory extends AbstractWicketFactory {

    @Override
    protected WebApplication createApplication(ApplicationContext applicationContext) {
        return new WicketApplication();
    }

}
