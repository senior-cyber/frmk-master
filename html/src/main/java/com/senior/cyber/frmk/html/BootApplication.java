package com.senior.cyber.frmk.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senior.cyber.frmk.html.factory.WicketFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BootApplication {

    public static void main(String[] args) throws Exception {
//        html(args);
        spring(args);
    }

    public static void html(String[] args) throws Exception {
        // git clone https://github.com/ColorlibHQ/AdminLTE.git
        File adminLTE = new File("AdminLTE");
        File folders = new File(adminLTE, "pages");
        List<File> files = new ArrayList<>();
        files.addAll(FileUtils.listFiles(folders, new String[]{"html"}, true));
        files.add(new File(adminLTE, "index.html"));
        files.add(new File(adminLTE, "index2.html"));
        files.add(new File(adminLTE, "index3.html"));
        files.add(new File(adminLTE, "starter.html"));
        files.add(new File(adminLTE, "iframe.html"));
        for (File file : files) {
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                line = StringUtils.trimToEmpty(line);
                if (line.contains("<a ")) {
                    if (line.contains("href=\"")) {
                        if (!line.contains("href=\"#") && !line.contains("href=\"https") && !line.contains("href=\"\"") && !line.contains("href=\"javascript")) {
                            System.out.println(file.getName() + " :: " + line);
                        }
                    }
                } else if (line.contains("<img")) {
                    if (!line.contains("wicket:id=\"") && !line.contains("src=\"https") && !line.contains("src=\"data")) {
                        if (i + 1 < lines.size()) {
                            String aheadLine = lines.get(i + 1);
                            if (!aheadLine.contains("wicket:id=\"") && !aheadLine.contains("src=\"https") && !aheadLine.contains("src=\"data")) {
                                System.out.println(file.getName() + " :: " + line);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void spring(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    public static ApplicationContext getApplicationContext() {
        return WicketFactory.getApplicationContext();
    }

    @Bean
    public Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
        return builder.create();
    }

}
