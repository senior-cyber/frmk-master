package com.senior.cyber.frmk.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senior.cyber.frmk.html.factory.WicketFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<String> newLines = new ArrayList<>(lines.size());
            boolean write = false;
            Map<String, Integer> indexer = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                String originalLine = lines.get(i);
                String line = StringUtils.trimToEmpty(originalLine);
                if (line.startsWith("<form") && line.contains("action")) {
                    if (!line.contains("action=\"#\"")) {
                        System.out.println(file.getName() + " :: " + line);
                        String search = "action=\"";
                        int bindex = originalLine.indexOf(search);
                        int lindex = originalLine.indexOf("\"", bindex + search.length());
                        String name = FilenameUtils.getName(originalLine.substring(search.length() + bindex, lindex));
                        if (indexer.get(name) == null) {
                            indexer.put(name, 1);
                        } else {
                            indexer.put(name, indexer.get(name) + 1);
                        }
                        String newLine = originalLine.substring(0, bindex) + "wicket:id=\"" + name + "_" + indexer.get(name) + originalLine.substring(lindex);
                        newLines.add(newLine);
                        write = true;
                    } else {
                        newLines.add(originalLine);
                    }
                } else if (line.contains("<a ") && !line.contains("href=\"#\"")) {
                    if (line.contains("href=\"")) {
                        if (!line.contains("href=\"#") && !line.contains("href=\"https") && !line.contains("href=\"\"") && !line.contains("href=\"javascript")) {
                            System.out.println(file.getName() + " :: " + line);
                            String search = "href=\"";
                            int bindex = originalLine.indexOf(search);
                            int lindex = originalLine.indexOf("\"", bindex + search.length());
                            String name = FilenameUtils.getName(originalLine.substring(search.length() + bindex, lindex));
                            if (indexer.get(name) == null) {
                                indexer.put(name, 1);
                            } else {
                                indexer.put(name, indexer.get(name) + 1);
                            }
                            String newLine = originalLine.substring(0, bindex) + "wicket:id=\"" + name + "_" + indexer.get(name) + originalLine.substring(lindex);
                            newLines.add(newLine);
                            write = true;
                        } else {
                            newLines.add(originalLine);
                        }
                    } else {
                        newLines.add(originalLine);
                    }
                } else if (line.contains("<img")) {
                    if (!line.contains("wicket:id=\"")) {
                        if (!line.contains("src=\"https") && !line.contains("src=\"data")) {
                            if (i + 1 < lines.size()) {
                                String aheadLineOriginal = lines.get(i + 1);
                                String aheadLine = StringUtils.trim(aheadLineOriginal);
                                if (aheadLine.startsWith("src=\"") && aheadLine.endsWith("\"")) {
                                    newLines.add(line);

                                    String search = "src=\"";
                                    int bindex = aheadLineOriginal.indexOf(search);
                                    int lindex = aheadLineOriginal.indexOf("\"", bindex + search.length());
                                    String name = FilenameUtils.getName(aheadLineOriginal.substring(search.length() + bindex, lindex));
                                    if (indexer.get(name) == null) {
                                        indexer.put(name, 1);
                                    } else {
                                        indexer.put(name, indexer.get(name) + 1);
                                    }
                                    String newLine = aheadLineOriginal.substring(0, bindex) + "wicket:id=\"" + name + "_" + indexer.get(name) + aheadLineOriginal.substring(lindex);
                                    newLines.add(newLine);

                                    i = i + 1;
                                    write = true;
                                } else if (!aheadLineOriginal.contains("wicket:id=\"") && !aheadLineOriginal.contains("src=\"https") && !aheadLineOriginal.contains("src=\"data")) {
                                    System.out.println(file.getName() + " :: " + line);
                                    String search = "src=\"";
                                    int bindex = originalLine.indexOf(search);
                                    int lindex = originalLine.indexOf("\"", bindex + search.length());
                                    String name = FilenameUtils.getName(originalLine.substring(search.length() + bindex, lindex));
                                    if (indexer.get(name) == null) {
                                        indexer.put(name, 1);
                                    } else {
                                        indexer.put(name, indexer.get(name) + 1);
                                    }
                                    String newLine = originalLine.substring(0, bindex) + "wicket:id=\"" + name + "_" + indexer.get(name) + originalLine.substring(lindex);
                                    newLines.add(newLine);
                                    write = true;
                                }
                            }
                        }
                    } else {
                        newLines.add(lines.get(i));
                    }
                } else {
                    newLines.add(lines.get(i));
                }
            }
            if (write) {
                FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), newLines);
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
