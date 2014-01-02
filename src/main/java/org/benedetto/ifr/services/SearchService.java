package org.benedetto.ifr.services;

import com.google.gson.Gson;
import org.benedetto.ifr.flickr.FlickrCache;
import org.benedetto.ifr.flickr.InvalidCacheRequestException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 *
 * This class exposes the Search Functionality of the FlickrCache via Rest.
 * This is part of the demo frontend.
 *
 **/

public class SearchService {

    FlickrCache cache;

    public SearchService(FlickrCache cache) {
        this.cache = cache;
    }

    @GET
    @Path("search_flickr")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchFlickr(
            @QueryParam("number") int number,
            @QueryParam("tags") String tags) throws IOException, InvalidCacheRequestException {
        checkArgument(number > 0);
        checkArgument(! tags.equals(""));
        List<String> urlPairs = cache.getImageUrls(number, tags);
        return new Gson().toJson(urlPairs);
    }
}
