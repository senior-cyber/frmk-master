### application.yaml
```yaml
spring:
  mvc:
    servlet:
      load-on-startup: ${override.spring.mvc.servlet.load-on-startup:1}
      path: ${override.spring.mvc.servlet.path:/api}
webui:
  servlet:
    load-on-startup: ${override.webui.servlet.load-on-startup:2}
    path: ${override.webui.servlet.path:/*}
  configuration-type: ${override.webui.configuration-type:DEVELOPMENT}
  csrf: ${override.webui.csrf:false}
  wicket-factory: ${override.webui.wicket-factory} # Wicket Factory Class
  pages: ${override.webui.pages} # Pages packages
  admin-lte: ${override.webui.admin-lte} # Admin LTE
```