package org.benedetto.ifr.util;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class HttpClientWrapper {

    public static String get(final String url) throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        client.executeMethod(method);
        byte[] responseBody = method.getResponseBody();
        method.releaseConnection();
        return new String(responseBody);
    }

    public static String postJson(final String url, final Object objectToPost) throws IOException {
        HttpClient client = new HttpClient();
        String jsonToPost = new Gson().toJson(objectToPost);
        StringRequestEntity requestEntity = new StringRequestEntity(
                jsonToPost,
                "application/json",
                "UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestEntity(requestEntity);
        client.executeMethod(postMethod);
        byte[] responseBody = postMethod.getResponseBody();
        postMethod.releaseConnection();
        return new String(responseBody);
    }

}
