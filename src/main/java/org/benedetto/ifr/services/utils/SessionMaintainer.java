package org.benedetto.ifr.services.utils;

import org.benedetto.ifr.adjancencylist.FeedBackConsumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMaintainer {

    public static FeedBackConsumer<String> consumerFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        FeedBackConsumer<String> consumer =
                (FeedBackConsumer<String>) session.getAttribute("consumer");
        if (consumer == null) {
            consumer = new FeedBackConsumer<>();
            session.setAttribute("consumer", consumer);
        }
        return consumer;
    }

}
