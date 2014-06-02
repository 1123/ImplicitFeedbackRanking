package org.benedetto.ifr.services;

import org.benedetto.ifr.flickr.FlickrCache;

import javax.ws.rs.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This is the Main RestApiService class.
 * It delegates work to subresources, that fulfill parts of the API.
 *
 * Jersey will only pick up the first class within this directory as a root resource.
 * It will not pick up this class if one of the subresource classes appears before this class
 * in lexicographical order.
 **/

@Path("/")
public class RestApiService {

    static FlickrCache cache = new FlickrCache();

    @Path("feedback")
    public RestFeedBackService feedBackService() {
        return new RestFeedBackService(cache);
    }

    @Path("searcher")
    public RestSearchService searchService() {
        return new RestSearchService(cache);
    }

    @Path("sorter")
    public RestSortService sortService() {
        return new RestSortService(cache);
    }

    @Path("graph")
    public GraphPropertyService graphService() {
        return new GraphPropertyService();
    }

}

