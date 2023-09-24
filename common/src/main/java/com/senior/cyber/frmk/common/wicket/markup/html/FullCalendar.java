package com.senior.cyber.frmk.common.wicket.markup.html;

import com.senior.cyber.frmk.common.wicket.resource.CalendarOnDomReady;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.IRequestListener;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class FullCalendar extends WebComponent implements IRequestListener {

    private static final long serialVersionUID = 1L;

    private FullCalendarProvider provider;

    public FullCalendar(String id, FullCalendarProvider provider) {
        super(id);
        this.provider = provider;
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String url = this.urlForListener(new PageParameters()).toString();
        String markupId = getMarkupId(true);
        String javascript = "var calendar_el_" + markupId + " = document.getElementById('" + markupId + "');" + "\n";
        javascript = javascript + "var calendar_" + markupId + " = new FullCalendar.Calendar(calendar_el_" + markupId + ",{navLinks: true, initialView: 'timeGridWeek', headerToolbar: { left  : 'prev,next today', center: 'title', right : 'dayGridMonth,timeGridWeek,timeGridDay' }, themeSystem: 'bootstrap', eventSources:['" + url + "']});" + "\n";
        javascript = javascript + "calendar_" + markupId + ".render();" + "\n";
        response.render(new CalendarOnDomReady(javascript));
    }

    @Override
    public void onRequest() {
        RequestCycle requestCycle = RequestCycle.get();
        Request request = requestCycle.getRequest();
        IRequestParameters params = request.getRequestParameters();

        String start = params.getParameterValue("start").toString("");
        String end = params.getParameterValue("end").toString("");

        Date startDate = null;
        try {
            startDate = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(start);
        } catch (ParseException e) {
            throw new WicketRuntimeException(e);
        }
        Date endDate = null;
        try {
            endDate = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            throw new WicketRuntimeException(e);
        }

        List<FullCalendarItem> options = this.provider.doQuery(startDate, endDate);

        WebResponse webResponse = (WebResponse) requestCycle.getResponse();
        webResponse.setContentType("application/json");

        OutputStreamWriter stream = new OutputStreamWriter(webResponse.getOutputStream(), getRequest().getCharset());
        try {
            stream.write("[" + StringUtils.join(options, ",") + "]");
            stream.flush();
        } catch (IOException e) {
        }
    }

    @Override
    public boolean rendersPage() {
        return false;
    }
}
