package org.benedetto.ifr.launch;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatStarter implements Runnable {

    private final Tomcat tomcat;
    private final String port;

    public TomcatStarter(final String port) {
        tomcat = new Tomcat();
        this.port = port;
    }

    public void stop() throws LifecycleException {
        tomcat.stop();
    }

    @Override
    public void run() {
        String webappDirLocation = "src/main/webapp/";
        tomcat.setPort(Integer.valueOf(port));
        try {
            tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        tomcat.getServer().await();
    }
}
