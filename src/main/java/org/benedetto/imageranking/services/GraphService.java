package org.benedetto.imageranking.services;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

public class GraphService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraph(@Context HttpServletRequest req) {
        return new Gson().toJson(req.getSession().getAttribute("graph"));
    }

}
