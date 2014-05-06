package org.benedetto.ifr.expedia;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;

import java.util.List;

public class ExpediaFeedback extends FeedBack<Integer> {

    Integer srch_id;

    public ExpediaFeedback(int page, List<Integer> chosen, Integer srch_id)
            throws InvalidFeedBackException {
        super(page, chosen);
        this.srch_id = srch_id;
    }

}
