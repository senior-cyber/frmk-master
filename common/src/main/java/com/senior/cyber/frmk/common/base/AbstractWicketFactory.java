package com.senior.cyber.frmk.common.base;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractWicketFactory implements IWebApplicationFactory {

    private static ApplicationContext applicationContext;

    @Override
    public final WebApplication createApplication(WicketFilter filter) {
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filter.getFilterConfig().getServletContext());
        return createApplication(applicationContext);
    }

    protected abstract WebApplication createApplication(ApplicationContext applicationContext);

    @Override
    public void destroy(WicketFilter filter) {

    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new WicketRuntimeException("NPE");
        }
        return applicationContext;
    }

}
