package org.benedetto.imageranking.util;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URISyntaxException;

public class GrizzlyStarter implements Runnable {

    final static String BASE_URI = "http://localhost:8080/image_ranking/";
    ResourceConfig config = new ResourceConfig().packages("org");
    private HttpServer server = null;

    public static void main(String [] args)
            throws IOException, URISyntaxException, ClassNotFoundException {
        GrizzlyStarter grizzlyStarter = new GrizzlyStarter();
        grizzlyStarter.run();
    }

    @Override
    public void run() {
        try {
            server = GrizzlyHttpServerFactory.createHttpServer(new java.net.URI(BASE_URI), config);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("./src/main/resources"), "/");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.stop();
    }
}
