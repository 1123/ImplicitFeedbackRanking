package org.benedetto.ifr.expedia;

import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

public class ClickStatisticsComputer {

    MongoClient client;
    DB db;
    DBCollection feedbackCollection;

    public ClickStatisticsComputer() throws UnknownHostException {
        this.client = new MongoClient();
        this.db = client.getDB("expedia");
        this.feedbackCollection = db.getCollection("feedback");
    }

    public void read() {
        Gson gson = new Gson();
        DBCursor cursor = this.feedbackCollection.find();
        int[][] clickStatisticsArray = new int[37][37];
        while (cursor.hasNext()) {
            ExpediaFeedback feedback = gson.fromJson(cursor.next().toString(), ExpediaFeedback.class);
            int pageLength = feedback.page.size();
            int selectedPosition = feedback.page.indexOf(feedback.chosen);
            if (pageLength >= 37) continue;
            if (selectedPosition >= 37) continue;
            clickStatisticsArray[selectedPosition][pageLength]++;
        }
        this.printAsCsv(clickStatisticsArray);
    }

    private void printAsCsv(int [][] statistics) {
        for (int i = 0; i < 37; i++) {
            for (int j = 0; j < 37; j++) {
                System.err.print(statistics[i][j] + ",");
            }
            System.err.println();
        }
    }

}
