package org.benedetto.ifr.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.AwgFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.flickr.FlickrCache;
import org.benedetto.ifr.services.utils.SessionMaintainer;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 *
 * This class exposes the Functionality of the AwgFeedBackConsumer via REST.
 * It is part of the Backend.
 *
 **/

public class FeedBackService {

    FlickrCache cache;

    public FeedBackService(FlickrCache cache) {
        this.cache = cache;
    }

    //@GET
    //@Produces(MediaType.APPLICATION_JSON)
    //public String getGraph(@Context HttpServletRequest req) {
    //    FeedBackConsumer consumer = (FeedBackConsumer) req.getSession().getAttribute("consumer");
    //    return new Gson().toJson(consumer.acyclicWeightedGraph);
    //}

    @POST
    @Path("click")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerClick(@Context HttpServletRequest req, final String feedBack)
            throws SQLException, InvalidExpansionStateException, InvalidAhrszStateException {
        checkArgument(! feedBack.equals(""));
        FeedBackConsumer<String> consumer = SessionMaintainer.consumerFromSession(req);
        // deserialization with Gson and generic types:
        // see : http://stackoverflow.com/questions/8989899/gson-deserialize-collections
        FeedBack<String> fb = new FeedBack<>();
        Type listType = TypeToken.get(fb.getClass()).getType();
        fb = new Gson().fromJson(feedBack, listType);
        consumer.addFeedBack(fb);
        return "OK";
    }

    @GET
    @Path("click_statistics")
    @Produces(MediaType.APPLICATION_JSON)
    public String getClickStatistics(@Context HttpServletRequest req) {
        FeedBackConsumer<String> consumer = SessionMaintainer.consumerFromSession(req);
        return new Gson().toJson(consumer.getStatistics());
    }

}
