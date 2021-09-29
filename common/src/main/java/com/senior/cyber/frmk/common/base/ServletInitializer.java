package com.senior.cyber.frmk.common.base;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;

public abstract class ServletInitializer extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        File temp = new File(servletContext.getRealPath(""));
        File projectFile = new File(temp.getParent(), temp.getName() + ".properties");
        if (projectFile.exists() && projectFile.isFile() && projectFile.canRead()) {
            System.setProperty("spring.config.location", projectFile.getAbsolutePath());
        }
        super.onStartup(servletContext);
        System.setProperty("spring.config.location", "");
    }

}
