package org.benedetto.ifr.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.benedetto.ifr.adjancencylist.ClosureGraph;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.adjancencylist.FeedBackConsumer;
import org.benedetto.ifr.flickr.FlickrCache;
import org.benedetto.ifr.flickr.InvalidCacheRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This is the Main ApiService class.
 * It delegates work to subresources, that fulfill parts of the API.
 *
 * Jersey will only pick up the first class within this directory as a root resource.
 * It will not pick up this class if one of the subresource classes appears before this class
 * in lexicographical order.
 **/

@Path("/")
public class ApiService {

    static FlickrCache cache = new FlickrCache();

    @Path("feedback")
    public FeedBackService feedBackService() {
        return new FeedBackService(cache);
    }

    @Path("searcher")
    public SearchService searchService() {
        return new SearchService(cache);
    }

    @Path("sorter")
    public SortService sortService() {
        return new SortService(cache);
    }

}

