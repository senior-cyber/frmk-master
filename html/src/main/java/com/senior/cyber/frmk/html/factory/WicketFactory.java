package com.senior.cyber.frmk.html.factory;

import com.senior.cyber.frmk.common.base.AbstractWicketFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.ApplicationContext;

public class WicketFactory extends AbstractWicketFactory {

    @Override
    protected WebApplication createApplication(ApplicationContext applicationContext) {
        return new WicketApplication();
    }

}
