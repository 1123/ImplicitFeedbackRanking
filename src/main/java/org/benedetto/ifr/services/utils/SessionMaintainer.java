package org.benedetto.ifr.services.utils;

import org.benedetto.ifr.feedback.AwgFeedBackConsumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMaintainer {

    public static AwgFeedBackConsumer<String> consumerFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        AwgFeedBackConsumer<String> consumer =
                (AwgFeedBackConsumer<String>) session.getAttribute("consumer");
        if (consumer == null) {
            consumer = new AwgFeedBackConsumer<>();
            session.setAttribute("consumer", consumer);
        }
        return consumer;
    }

}
