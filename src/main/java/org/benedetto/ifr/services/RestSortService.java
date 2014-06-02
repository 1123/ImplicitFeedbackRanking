package org.benedetto.ifr.services;

import com.google.gson.Gson;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;
import org.benedetto.ifr.flickr.FlickrCache;
import org.benedetto.ifr.flickr.InvalidCacheRequestException;
import org.benedetto.ifr.services.utils.SessionMaintainer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class RestSortService {

    FlickrCache cache;

    public RestSortService(FlickrCache cache) {
        this.cache = cache;
    }

    @GET
    @Path("sort")
    @Produces(MediaType.APPLICATION_JSON)
    public String topologicalSort(
            @QueryParam("tags") String tags,
            @Context HttpServletRequest req) throws IOException, InvalidCacheRequestException {
        checkArgument(! tags.equals(""));
        FeedBackConsumer<String> consumer = SessionMaintainer.consumerFromSession(req);
        List<String> photoDetails =
                cache.getImageUrls(FlickrCache.querySize, tags);
        consumer.sort(photoDetails);
        return new Gson().toJson(photoDetails);
    }

}
