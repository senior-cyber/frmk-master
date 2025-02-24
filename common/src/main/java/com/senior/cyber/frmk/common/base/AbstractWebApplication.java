package com.senior.cyber.frmk.common.base;

import com.senior.cyber.frmk.common.wicket.resource.JQueryMinJS;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.core.util.crypt.KeyInSessionSunJceCryptFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.*;
import org.apache.wicket.resource.FileSystemResourceReference;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class AbstractWebApplication extends WebApplication implements IResourceReferenceFactory, LTEAdminProperties {

    private WebUiProperties webUiProperties;

    protected AbstractWebApplication(WebUiProperties webUiProperties) {
        this.webUiProperties = webUiProperties;
    }

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking().disabled();
        if (this.webUiProperties.isCsrf()) {
            getSecuritySettings().setCryptFactory(new KeyInSessionSunJceCryptFactory());
            setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
        } else {
            var pages = this.webUiProperties.getPages();
            if (pages != null && !"".equals(pages)) {
                var reflections = new Reflections(new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(pages))
                        .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated));
                var clazzes = reflections.getTypesAnnotatedWith(Bookmark.class);
                var bookmarks = new TreeSet<String>();
                var existedPages = new HashMap<String, Class<?>>();
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
        File adminLte = webUiProperties.getAdminLte();
        FileSystemResourceReference reference = new FileSystemResourceReference(new File(adminLte, AdminLTEResourceReference.JS_JQUERY).getPath());
        getJavaScriptLibrarySettings().setJQueryReference(reference);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return this.webUiProperties.getConfigurationType();
    }

    public WebUiProperties getWebUiProperties() {
        return this.webUiProperties;
    }

    @Override
    protected ResourceReferenceRegistry newResourceReferenceRegistry() {
        return new ResourceReferenceRegistry(this);
    }

}
