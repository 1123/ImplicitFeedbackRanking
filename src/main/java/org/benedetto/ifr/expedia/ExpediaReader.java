package org.benedetto.ifr.expedia;

import com.google.gson.Gson;
import com.mongodb.*;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;
import org.benedetto.ifr.util.Mapper;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExpediaReader {

    MongoClient client;
    DB db;
    DBCollection feedbackCollection;

    public ExpediaReader() throws UnknownHostException {
        this.client = new MongoClient();
        this.db = client.getDB("expedia");
        this.feedbackCollection = db.getCollection("feedback");
    }

    public void read() throws UnknownHostException, InvalidFeedBackException {
        Gson gson = new Gson();
        DBCollection coll = db.getCollection("clicks_training");
        DBObject order = new BasicDBObject();
        order.put("srch_id", 1);
        DBCursor cursor = coll.find().sort(order);
        int count = 0;
        int srch_id = 0;
        List<Click> clickBuffer = new ArrayList<>();
        while (cursor.hasNext() && count < 10000000) {
            Click click = gson.fromJson(cursor.next().toString(), Click.class);
            if (click.getSrch_id() > srch_id) {
                savePage(click.getSrch_id(), clickBuffer);
                clickBuffer = new ArrayList<>();
                srch_id = click.getSrch_id();
            }
            System.err.println(gson.toJson(click));
            clickBuffer.add(click);
            count++;
        }
        savePage(srch_id, clickBuffer);
        clickBuffer.clear();
    }

    private void savePage(int srch_id, List<Click> clickBuffer) throws InvalidFeedBackException {
        List<Integer> page = new ArrayList<>();
        for (Click c: clickBuffer) {
            page.add(c.position);
        }
        for (Click c : clickBuffer) {
            if (c.click_bool != 1) continue;
            // one srch_id may result in multiple feedbacks.
            ExpediaFeedback feedBack = new ExpediaFeedback(c.position, page, srch_id);
            feedbackCollection.save(new Feedback2DBObject<Integer>().map(feedBack));
        }
    }

}

