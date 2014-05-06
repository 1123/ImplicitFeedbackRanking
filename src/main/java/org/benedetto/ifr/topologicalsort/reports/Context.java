package org.benedetto.ifr.topologicalsort.reports;

import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

public class Context {

    private static VelocityEngine velocityEngine;

    static {
        velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty("file.resource.loader.path", "src/main/resources");
        velocityEngine.init(p);
    }

    public static VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

}
