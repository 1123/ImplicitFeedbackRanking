package org.benedetto.ifr.flickr;

import com.google.common.base.Function;
import com.sun.istack.Nullable;
import org.benedetto.ifr.util.Mapper;

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
                "http://farm%s.staticflickr.com/%s/%s_%s",
                farm, server, id, secret);
    }

    public static class PhotoDetails2Url implements Mapper<PhotoDetails, String> {

        @Override
        public String map(PhotoDetails details) {
            return details.getUrl();
        }
    }
}

