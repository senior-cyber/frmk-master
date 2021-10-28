package com.senior.cyber.frmk.common.base;

import com.senior.cyber.frmk.common.wicket.resource.JQueryMinJS;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.core.util.crypt.KeyInSessionSunJceCryptFactory;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.request.resource.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;

public abstract class WicketFactory extends org.apache.wicket.protocol.http.WebApplication
        implements IWebApplicationFactory, IResourceReferenceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WicketFactory.class);

    transient static ApplicationContext applicationContext;

    private WebUiProperties webUiProperties;

    public WicketFactory() {
    }

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking().disabled();
        if (this.webUiProperties.isCsrf()) {
            getSecuritySettings().setCryptFactory(new KeyInSessionSunJceCryptFactory());
            setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
        } else {
            if (this.webUiProperties.getPkg() != null && !"".equals(this.webUiProperties.getPkg())) {
                Reflections reflections = new Reflections(new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(this.webUiProperties.getPkg()))
                        .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated));
                Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(Bookmark.class);
                Set<String> bookmarks = new TreeSet<>();
                Map<String, Class<?>> existedPages = new HashMap<>();
                if (clazzes != null && !clazzes.isEmpty()) {
                    for (Class<?> clazz : clazzes) {
                        Bookmark bookmark = clazz.getAnnotation(Bookmark.class);
                        if (bookmark != null) {
                            if (!bookmarks.contains(bookmark.value())) {
                                bookmarks.add(bookmark.value());
                                mountPage(bookmark.value(), (Class<Page>) clazz);
                                existedPages.put(bookmark.value(), clazz);
                            } else {
                                Class<?> existedPage = existedPages.get(bookmark.value());
                                throw new WicketRuntimeException(bookmark.value() + " was duplicated between [" + existedPage.getName() + "] and [" + clazz.getName() + "]");
                            }
                        }
                    }
                }
            }
        }
        getJavaScriptLibrarySettings().setJQueryReference(JQueryMinJS.INSTANCE);
    }

    @Override
    public <T extends Page> MountedMapper mountPage(String path, Class<T> pageClass) {
        return super.mountPage(path, pageClass);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return getWebUiProperties().getConfigurationType();
    }

    protected WebUiProperties getWebUiProperties() {
        return this.webUiProperties;
    }

    @Override
    public org.apache.wicket.protocol.http.WebApplication createApplication(WicketFilter filter) {
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filter.getFilterConfig().getServletContext());
        this.webUiProperties = getApplicationContext().getBean(WebUiProperties.class);
        return this;
    }

    public static ApplicationContext getApplicationContext() {
        if (WicketFactory.applicationContext == null && AuthenticatedWicketFactory.applicationContext == null) {
            throw new WicketRuntimeException("NPE");
        }
        if (WicketFactory.applicationContext != null) {
            return WicketFactory.applicationContext;
        }
        return AuthenticatedWicketFactory.applicationContext;
    }

    @Override
    protected ResourceReferenceRegistry newResourceReferenceRegistry() {
        return new ResourceReferenceRegistry(this);
    }

    @Override
    public ResourceReference create(ResourceReference.Key key) {
        if (AdminLTEResourceReference.class.getName().equals(key.getScope())) {
            return new AdminLTEResourceReference(key.getName());
        } else {
            ResourceReference result = null;
            if (PackageResource.exists(key)) {
                result = new PackageResourceReference(key);
            }
            return result;
        }
    }

    @Override
    public void destroy(WicketFilter filter) {
    }

}
