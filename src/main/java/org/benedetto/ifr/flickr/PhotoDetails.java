package org.benedetto.ifr.flickr;

import com.google.common.base.Function;
import com.sun.istack.Nullable;

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

    public static class PhotoDetails2Url implements Function<PhotoDetails, String> {

        @Override
        public String apply(@Nullable PhotoDetails photoDetails) {
            return photoDetails.getUrl();
        }

    }
}

