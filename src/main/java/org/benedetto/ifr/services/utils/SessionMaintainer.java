package org.benedetto.ifr.services.utils;

import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * This class is responsible for getting the feedbackconsumer for a given http session.
 * In case there is no consumer registered, a new one will be created and added to the session.
 */

public class SessionMaintainer {

    public static FeedBackConsumer<String> consumerFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        FeedBackConsumer<String> consumer =
                (FeedBackConsumer<String>) session.getAttribute("consumer");
        if (consumer == null) {
            consumer = new AhrszFeedBackConsumer<>();
            session.setAttribute("consumer", consumer);
        }
        return consumer;
    }

}
