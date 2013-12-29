package org.benedetto.ifr.api;

import com.google.gson.Gson;
import launch.Main;
import launch.TomcatStarter;
import org.apache.catalina.LifecycleException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.util.HttpClientWrapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 12/28/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class RestApiTest {

    private static final int pageSize = 20;
    private static final String host = "localhost";
    private static final int port = 9090;
    static Thread tomcatThread;

    private static String searchFlickrUrl() {
        return String.format(
                "http://%s:%s/api/graph/search_flickr?tags=Ferienhaus&number=%d",
                host, port, pageSize
        );
    }

    private static String postFeedbackUrl() {
        return String.format("http://%s:%s/api/graph/click", host, port);
    }

    @BeforeClass
    public static void setup() throws ServletException, LifecycleException, InterruptedException {
        tomcatThread = new Thread(new TomcatStarter(port + ""));
        tomcatThread.start();
        Thread.sleep(20000);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        String result = HttpClientWrapper.get(searchFlickrUrl());
        List<String> images = new Gson().fromJson(result, ArrayList.class);
        int selected = new Random().nextInt(pageSize);
        FeedBack feedBack = new FeedBack(images.get(selected), images);
        System.err.println(new Gson().toJson(feedBack));
        String postResult = HttpClientWrapper.postJson(postFeedbackUrl(), feedBack);
        System.err.println(postResult);
        Thread.sleep(5000);
    }

    @AfterClass
    public static void tearDown() throws LifecycleException {
        tomcatThread.interrupt();
    }

}
