package org.benedetto.ifr.flickr;

public class PhotoDetails {
    String id;
    String owner;
    String secret;
    int server;
    int farm;
    String title;
    int ispublic;
    int isfriend;
    int isfamily;

    public String getUrl() {
        return String.format(
                "http://farm%s.staticflickr.com/%s/%s_%s_m.jpg",
                farm, server, id, secret);
    }
}

