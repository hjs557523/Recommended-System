package com.dc.base.listener;

import com.dc.base.contants.BaseContants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class WebappListener implements ServletContextListener {
    //将日志输出在指定目录下
    public void contextInitialized(ServletContextEvent sce) {
        String webAppRootKey = sce.getServletContext().getRealPath(File.separator);
        System.setProperty(BaseContants.WEBAPP_ROOT, webAppRootKey + File.separator);
        File file = new File(webAppRootKey);
        System.setProperty(BaseContants.PROJECT_NAME, file.getName());
        System.setProperty(BaseContants.CLASSES, webAppRootKey + File.separator
                + "WEB-INF" + File.separator + "classes" + File.separator);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
