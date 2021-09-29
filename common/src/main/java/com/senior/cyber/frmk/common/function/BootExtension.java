package com.senior.cyber.frmk.common.function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FilePermission;
import java.security.Permission;
import java.security.ProtectionDomain;
import java.util.Enumeration;

public class BootExtension {

    public static ConfigurableApplicationContext run(Class<?> mainClass, String... args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(mainClass);
        boolean commandLineProperties = false;
        if (args != null) {
            for (String arg : args) {
                if (arg.startsWith("--spring.config.location")) {
                    commandLineProperties = true;
                    break;
                }
            }
        }
        if (!commandLineProperties) {
            String externalProperties = BootExtension.externalProperties(mainClass);
            if (externalProperties != null) {
                args = new String[]{"--spring.config.location=" + externalProperties};
            }
        }
        SpringApplication application = builder.build(args);
        return application.run(args);
    }

    private static String externalProperties(Class<?> mainClass) {
        String mainJar = null;
        String homeDirectory = FileUtils.getUserDirectoryPath();
        String currentDirectory = System.getProperty("user.dir");
        ProtectionDomain protectionDomain = mainClass.getProtectionDomain();
        Enumeration<Permission> permissions = protectionDomain.getPermissions().elements();
        String jarDirectory = null;
        while (permissions.hasMoreElements()) {
            Permission permission = permissions.nextElement();
            if (permission instanceof FilePermission) {
                File mainJarFile = new File(permission.getName());
                mainJar = mainJarFile.getName();
                jarDirectory = mainJarFile.getParent();
            }
        }

        if ("-".equals(mainJar)) {
            return null;
        }

        File properties = null;
        if (currentDirectory.equals(jarDirectory)) {
            properties = new File(jarDirectory, FilenameUtils.getBaseName(mainJar) + ".properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(jarDirectory, "application.properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(homeDirectory, FilenameUtils.getBaseName(mainJar) + ".properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(homeDirectory, "application.properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
        } else {
            properties = new File(currentDirectory, FilenameUtils.getBaseName(mainJar) + ".properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(currentDirectory, "application.properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(jarDirectory, FilenameUtils.getBaseName(mainJar) + ".properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(jarDirectory, "application.properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(homeDirectory, FilenameUtils.getBaseName(mainJar) + ".properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
            properties = new File(homeDirectory, "application.properties");
            if (properties.exists() && properties.isFile() && properties.canRead()) {
                return properties.getAbsolutePath();
            }
        }
        return null;
    }

}
