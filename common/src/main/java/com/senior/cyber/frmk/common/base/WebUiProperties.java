package com.senior.cyber.frmk.common.base;

import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.File;
import java.io.Serializable;

@ConfigurationProperties(prefix = "webui")
public class WebUiProperties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7645741440087458926L;

    private RuntimeConfigurationType configurationType = RuntimeConfigurationType.DEPLOYMENT;

    private String pages;

    private boolean csrf;

    private Class<?> wicketFactory = null;

    private File adminLte;

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public File getAdminLte() {
        return adminLte;
    }

    public void setAdminLte(File adminLte) {
        this.adminLte = adminLte;
    }

    private final Servlet servlet = new Servlet();

    public Servlet getServlet() {
        return servlet;
    }

    public boolean isCsrf() {
        return csrf;
    }

    public void setCsrf(boolean csrf) {
        this.csrf = csrf;
    }

    public RuntimeConfigurationType getConfigurationType() {
        return configurationType;
    }

    public void setConfigurationType(RuntimeConfigurationType configurationType) {
        this.configurationType = configurationType;
    }

    public Class<?> getWicketFactory() {
        return wicketFactory;
    }

    public void setWicketFactory(Class<?> wicketFactory) {
        this.wicketFactory = wicketFactory;
    }

    public static class Servlet {

        /**
         * Path of the dispatcher servlet.
         */
        private String path = "/*";

        /**
         * Load on startup priority of the dispatcher servlet.
         */
        private int loadOnStartup = -1;

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            Assert.notNull(path, "Path must not be null");
            this.path = path;
        }

        public int getLoadOnStartup() {
            return this.loadOnStartup;
        }

        public void setLoadOnStartup(int loadOnStartup) {
            this.loadOnStartup = loadOnStartup;
        }

        public String getServletMapping() {
            if (this.path.equals("") || this.path.equals("/") || this.path.equals("/*")) {
                return "/*";
            }
            if (this.path.endsWith("/")) {
                return this.path + "*";
            }
            return this.path + "/*";
        }

        public String getPath(String path) {
            String prefix = getServletPrefix();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            return prefix + path;
        }

        public String getServletPrefix() {
            String result = this.path;
            int index = result.indexOf('*');
            if (index != -1) {
                result = result.substring(0, index);
            }
            if (result.endsWith("/")) {
                result = result.substring(0, result.length() - 1);
            }
            return result;
        }

    }

}
