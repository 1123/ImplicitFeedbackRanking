package org.benedetto.ifr.services;

import com.google.gson.Gson;
import org.benedetto.ifr.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.flickr.FlickrRestClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("graph")
public class GraphService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraph(@Context HttpServletRequest req) {
        AcyclicWeightedGraph awg = (AcyclicWeightedGraph) req.getSession().getAttribute("graph");
        return new Gson().toJson(awg);
    }

    @GET @Path("search_flickr")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchFlickr(
            @QueryParam("number") int number,
            @QueryParam("tag") String tag) throws IOException {
        FlickrRestClient flickrRestClient = new FlickrRestClient();
        List<String> urls = flickrRestClient.getImageUrls(number, tag);
        return new Gson().toJson(urls);
    }

    @Path("click") @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerClick(@Context HttpServletRequest req, final String feedBack) throws SQLException {
        HttpSession session = req.getSession(true);
        AcyclicWeightedGraph acyclicWeightedGraph = (AcyclicWeightedGraph) session.getAttribute("graph");
        if (acyclicWeightedGraph == null) {
            acyclicWeightedGraph = new AcyclicWeightedGraph();
            session.setAttribute("graph", acyclicWeightedGraph);
        }
        FeedBack fb = new Gson().fromJson(feedBack, FeedBack.class);
        acyclicWeightedGraph.addFeedBack(fb);
        return "OK";
    }


}
