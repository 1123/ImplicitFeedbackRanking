package org.benedetto.ifr.expedia;

import com.google.gson.Gson;
import com.mongodb.*;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExpediaReader {

    private List<Click> clickBuffer;

    public ExpediaReader() {
        this.clickBuffer = new ArrayList<>();
    }

    public void read() throws UnknownHostException, InvalidFeedBackException {
        Gson gson = new Gson();
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("expedia");
        DBCollection coll = db.getCollection("clicks_training");
        DBObject order = new BasicDBObject();
        order.put("srch_id", -1);
        DBCursor cursor = coll.find().sort(order);
        int count = 0;
        int srch_id = 0;
        while (cursor.hasNext() && count < 10) {
            Click click = gson.fromJson(cursor.next().toString(), Click.class);
            if (click.getSrch_id() > srch_id) {
                savePage();
                srch_id = click.getSrch_id();
            }
            System.err.println(gson.toJson(click));
            this.clickBuffer.add(click);
            count++;
        }
    }

    private void savePage() throws InvalidFeedBackException {
        List<Integer> page = new ArrayList<>();
        for (Click c: this.clickBuffer) {
            page.add(c.position);
        }
        for (Click c : this.clickBuffer) {
            if (c.click_bool != 1) continue;
            FeedBack<Integer> feedBack = new FeedBack<>(c.position, page);
        }
    }

}
