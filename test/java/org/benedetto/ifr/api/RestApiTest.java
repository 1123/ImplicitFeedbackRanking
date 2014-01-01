package org.benedetto.ifr.api;

import com.google.gson.Gson;
import launch.TomcatStarter;
import org.apache.catalina.LifecycleException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.util.HttpClientWrapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 12/28/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class RestApiTest {

    private static final int pageSize = 3;
    private static final String host = "localhost";
    private static final int port = 9090;
    private static String tags = "Ferienhaus";
    static Thread tomcatThread;

    private static String searchFlickrUrl() {
        return String.format(
                "http://%s:%s/api/graph/search_flickr?tags=%s&number=%d",
                host, port, tags, pageSize
        );
    }

    private static String postFeedbackUrl() {
        return String.format("http://%s:%s/api/graph/click", host, port);
    }

    private static String sortedImagesUrl() {
        return String.format("http://%s:%s/api/graph/sort?tags=%s", host, port, tags);
    }

    @BeforeClass
    public static void setup() throws ServletException, LifecycleException, InterruptedException {
        tomcatThread = new Thread(new TomcatStarter(port + ""));
        tomcatThread.start();
        Thread.sleep(20000);
    }

    //@Test // this won't work, since httpclient does not use session cookies by default.
    public void test() throws IOException, InterruptedException {
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        String result = HttpClientWrapper.get(searchFlickrUrl());
        @SuppressWarnings("unchecked")
        List<String> images = new Gson().fromJson(result, ArrayList.class);
        String selected = images.get(new Random().nextInt(pageSize));
        FeedBack<String> feedBack = new FeedBack<>(selected, images);
        System.err.println(new Gson().toJson(feedBack));
        String postResult = HttpClientWrapper.postJson(postFeedbackUrl(), feedBack);
        System.err.println(postResult);
        @SuppressWarnings("unchecked")
        List<String> sortedImages = new Gson().fromJson(
                HttpClientWrapper.get(sortedImagesUrl()),
                ArrayList.class
        );
        System.err.println(sortedImages);
        int positionOfSelected = sortedImages.indexOf(selected);
        for (String image : images) {
            System.err.println(sortedImages.indexOf(image) + "; " + positionOfSelected);
            assertTrue(sortedImages.indexOf(image) >= positionOfSelected);
        }
    }

    @AfterClass
    public static void tearDown() throws LifecycleException {
        tomcatThread.interrupt();
    }

}
