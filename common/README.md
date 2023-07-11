### application.yaml
```yaml
spring:
  mvc:
    servlet:
      load-on-startup: ${spring-mvc-servlet-load-on-startup:1}
      path: ${spring-mvc-servlet-path:/api}
webui:
  servlet:
    load-on-startup: ${webui-servlet-load-on-startup:2}
    path: ${webui-servlet-path:/*}
  configuration-type: ${webui-configuration-type:DEVELOPMENT}
  csrf: ${webui-csrf:false}
  wicket-factory: # Wicket Factory Class
  pkg: # Pages packages
  admin-lte: # Admin LTE
```