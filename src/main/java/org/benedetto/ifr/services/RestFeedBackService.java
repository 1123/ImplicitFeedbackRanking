package org.benedetto.ifr.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;
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

public class RestFeedBackService {

    FlickrCache cache;

    public RestFeedBackService(FlickrCache cache) {
        this.cache = cache;
    }

    @POST
    @Path("click")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerClick(@Context HttpServletRequest req, final String feedBack)
            throws SQLException, InvalidExpansionStateException, InvalidAhrszStateException, InvalidFeedBackException {
        checkArgument(! feedBack.equals(""));
        FeedBackConsumer<String> consumer = SessionMaintainer.consumerFromSession(req);
        Type feedBackStringType = new TypeToken<FeedBack<String>>() {}.getType();
        FeedBack<String> fb = new Gson().fromJson(feedBack, feedBackStringType);
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
