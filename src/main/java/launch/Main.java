package launch;

import org.apache.catalina.startup.Tomcat;

public class Main {

    public static Tomcat tomcat = new Tomcat();

    public static void main(String[] args) throws Exception {
        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "9090";
        }
        Thread thread = new Thread(new TomcatStarter(webPort));
        thread.run();
    }

}

