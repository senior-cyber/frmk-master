package com.senior.cyber.frmk.common.base;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnProperty(name = "webui.wicket-factory")
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebUiAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUiAutoConfiguration.class);

    @ConditionalOnClass(WicketFactory.class)
    @EnableConfigurationProperties({WebUiProperties.class, WebMvcProperties.class})
    static class WebUi {

        private final WebMvcProperties webMvcProperties;

        private final WebUiProperties webUiProperties;

        public WebUi(WebUiProperties webUiProperties, WebMvcProperties webMvcProperties) {
            this.webUiProperties = webUiProperties;
            this.webMvcProperties = webMvcProperties;
        }

        @Bean
        @ConditionalOnMissingBean(WicketServlet.class)
        public ServletRegistrationBean<WicketServlet> createWicketServlet() {
            LOGGER.info("spring.mvc.servlet.loadOnStartup {}", this.webMvcProperties.getServlet().getLoadOnStartup());
            LOGGER.info("spring.mvc.servlet.path {}", this.webMvcProperties.getServlet().getPath());
            WicketServlet servlet = new WicketServlet();
            ServletRegistrationBean<WicketServlet> bean = new ServletRegistrationBean<>(servlet, this.webUiProperties.getServlet().getPath());
            bean.setName(WicketServlet.class.getName());
            bean.setLoadOnStartup(this.webMvcProperties.getServlet().getLoadOnStartup() + 1);
            bean.addInitParameter(WicketFilter.APP_FACT_PARAM, this.webUiProperties.getWicketFactory().getName());
            bean.addInitParameter("filterMappingUrlPattern", this.webUiProperties.getServlet().getPath());
            bean.addInitParameter("ignorePaths", this.webMvcProperties.getServlet().getPath());
            return bean;
        }

    }

}
