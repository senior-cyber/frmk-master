package com.senior.cyber.frmk.common.base;

import com.senior.cyber.frmk.common.simulator.SimulateEnum;
import com.senior.cyber.frmk.common.simulator.SimulateHttpEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "webui")
public class WebUiProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private RuntimeConfigurationType configurationType = RuntimeConfigurationType.DEPLOYMENT;

    private Map<String, SimulateEnum> simulation;

    private String pages;

    private boolean csrf;

    private Class<?> wicketFactory = null;

    private File adminLte;

    private Servlet servlet = new Servlet();

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
