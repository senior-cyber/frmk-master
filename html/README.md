```shell
# --spring.config.location : directory with / at the end
# --spring.config.name : base file name
./gradlew bootJar bootRun --args='--spring.config.location=classpath:/application.yaml,file:./application.yaml'
```

## html/application.yaml
```yaml
webui-admin-lte: /Users/socheatkhauv/github/ColorlibHQ/AdminLTE
```

```shell
# JDK-7
${JAVA_HOME}/bin/java -jar build/libs/html.jar --spring.config.location=classpath:/application.yaml,file:./application.yaml

```