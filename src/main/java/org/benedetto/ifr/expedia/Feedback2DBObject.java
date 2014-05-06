package org.benedetto.ifr.expedia;

import com.mongodb.BasicDBObject;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.util.Mapper;

public class Feedback2DBObject <N> implements Mapper<FeedBack<N>, BasicDBObject> {

    @Override
    public BasicDBObject map(FeedBack<N> input) {
        BasicDBObject result = new BasicDBObject();
        result.put("chosen", input.chosen);
        result.put("page", input.page);
        return result;
    }
}
