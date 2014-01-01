package org.benedetto.ifr.services;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.benedetto.ifr.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.ifr.adjancencylist.ClosureGraph;
import org.benedetto.ifr.adjancencylist.FeedBack;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Path("graph")
public class GraphService {

    static FlickrCache cache = new FlickrCache();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraph(@Context HttpServletRequest req) {
        AcyclicWeightedGraph awg = (AcyclicWeightedGraph) req.getSession().getAttribute("graph");
        return new Gson().toJson(awg);
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

    @POST
    @Path("click")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerClick(@Context HttpServletRequest req, final String feedBack) throws SQLException {
        checkArgument(! feedBack.equals(""));
        AcyclicWeightedGraph<String> acyclicWeightedGraph = graphFromSession(req);
        // deserialization with Gson and generic types:
        // see : http://stackoverflow.com/questions/8989899/gson-deserialize-collections
        FeedBack<String> fb = new FeedBack<>();
        Type listType = TypeToken.get(fb.getClass()).getType();
        fb = new Gson().fromJson(feedBack, listType);
        acyclicWeightedGraph.addFeedBack(fb);
        return "OK";
    }

    @GET
    @Path("sort")
    @Produces(MediaType.APPLICATION_JSON)
    public String topologicalSort(
            @QueryParam("tags") String tags,
            @Context HttpServletRequest req) throws IOException, InvalidCacheRequestException {
        checkArgument(! tags.equals(""));
        AcyclicWeightedGraph<String> acyclicWeightedGraph = this.graphFromSession(req);
        ClosureGraph<String> closureGraph = new ClosureGraph<>(acyclicWeightedGraph);
        List<String> photoDetails =
                cache.getImageUrls(FlickrCache.querySize, tags);
        closureGraph.sort(photoDetails);
        return new Gson().toJson(photoDetails);
    }

    private AcyclicWeightedGraph<String> graphFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        AcyclicWeightedGraph<String> acyclicWeightedGraph =
                (AcyclicWeightedGraph<String>) session.getAttribute("graph");
        if (acyclicWeightedGraph == null) {
            acyclicWeightedGraph = new AcyclicWeightedGraph<>();
            session.setAttribute("graph", acyclicWeightedGraph);
        }
        return acyclicWeightedGraph;
    }

}
