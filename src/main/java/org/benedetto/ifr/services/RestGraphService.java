package org.benedetto.ifr.services;

import com.google.gson.Gson;
import org.benedetto.ifr.adjancencylist.Edge;
import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.benedetto.ifr.services.utils.SessionMaintainer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * This class is used to retrieve information about the comparison graph via REST.
 * Such information could be
 * * the most attractive nodes
 * * the least attractive nodes
 * * the edges with the most weight
 * * connected components
 * * unconnected nodes
 */

public class RestGraphService {

    @GET
    @Path("top_n_edges")
    @Produces(MediaType.APPLICATION_JSON)
    public String topNEdges(@Context HttpServletRequest req, @QueryParam("edges") Integer numberOfEdges) {
        GraphPropertyService graphPropertyService = new GraphPropertyService();
        AhrszFeedBackConsumer<String> consumer = (AhrszFeedBackConsumer<String>) SessionMaintainer.consumerFromSession(req);
        List<Edge<String>> edges = graphPropertyService.topNEdges(consumer.ahrsz.hashMapGraph, numberOfEdges);
        return new Gson().toJson(edges);
    }

    @GET
    @Path("top_n_nodes")
    @Produces(MediaType.APPLICATION_JSON)
    public String topNNodes(@Context HttpServletRequest req, @QueryParam("edges") Integer numberOfEdges) {
        GraphPropertyService graphPropertyService = new GraphPropertyService();
        AhrszFeedBackConsumer<String> consumer = (AhrszFeedBackConsumer<String>) SessionMaintainer.consumerFromSession(req);
        List<String> nodes = graphPropertyService.topNEdgeNodes(consumer.ahrsz.hashMapGraph, numberOfEdges);
        return new Gson().toJson(nodes);
    }

}
