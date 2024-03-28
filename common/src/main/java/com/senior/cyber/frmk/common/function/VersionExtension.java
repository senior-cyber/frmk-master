package com.senior.cyber.frmk.common.function;

import jakarta.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class VersionExtension {

    public static String getVersionPom(File pomFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            return null;
        }

        Document document = null;
        try {
            document = builder.parse(pomFile);
        } catch (SAXException | IOException e) {
            return null;
        }

        return document.getDocumentElement().getElementsByTagName("version").item(0).getTextContent() + " "
                + DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(new Date());
    }

    public static String getVersionManifest(ServletContext servletContext) {
        String manifest = "/META-INF/MANIFEST.MF";
        String absoluteDiskPath = servletContext.getRealPath(manifest);
        File file = new File(absoluteDiskPath);

        try {
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            String v = "";
            String t = "";
            for (String line : lines) {
                if (line != null && !"".equals(line)) {
                    if (line.startsWith("Version")) {
                        v = StringUtils.trimToEmpty(StringUtils.substring(line, "Version:".length()));
                    } else if (line.startsWith("Build-Time")) {
                        t = StringUtils.trimToEmpty(StringUtils.substring(line, "Build-Time:".length()));
                    }
                }
            }
            return v + " " + t;
        } catch (IOException e) {
            return null;
        }

    }

}
