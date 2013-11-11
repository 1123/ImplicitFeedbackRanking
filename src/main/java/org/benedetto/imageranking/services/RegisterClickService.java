package org.benedetto.imageranking.services;

import com.google.gson.Gson;
import org.benedetto.imageranking.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.imageranking.adjancencylist.FeedBack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("click")
public class RegisterClickService {

    // HttpServletRequest is always null. This seems to be a bug with grizzly?
    @Context HttpServletRequest req;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerClick(final String feedBack) throws SQLException {
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

