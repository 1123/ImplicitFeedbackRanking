package org.benedetto.ifr.topologicalsort.reports;

import com.google.gson.Gson;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.feedback.MaxAttractivityFeedbackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;
import org.benedetto.ifr.topologicalsort.SortDistance;

import java.io.*;
import java.util.List;

/*
 * How can we measure accuracy?
 * Accuracy should be the ratio between estimated and actual attractiveness
 * * actual attractiveness is a real number between 0 and 1
 * * we do not directly estimate attractiveness
 * * instead an order between all elements is estimated
 *
 * accuracy of the entire ordering is the average of accuracy of all items
 *
 * accuracy of an item is the distance between the item in the estimated
 * order and the actual order divided by the number of items.
 *
 * Hence the accuracy of the entire ordering is the sum of the distances
 * between all pairs of items in the two orders divided by the number
 * of items squared.
 */

public class AccuracyByClicksAndItems {
    static final int minItems = 10;
    static final int itemsStep = 20;
    static final int numItemMeasurements = 20;
    static final int minClicks = 10;
    static final int clicksStep = 20;
    static final int numClickMeasurements = 20;
    static final int iterations = 5;
    static final int pageSize = 10;

    public static void main(String [] args) throws InvalidExpansionStateException, InvalidAhrszStateException, FileNotFoundException, UnsupportedEncodingException {
        float [][] measurements = new float[numClickMeasurements][numItemMeasurements];
        for (int i = 0; i < numClickMeasurements; i++) {
            int clicks = minClicks + i * clicksStep;
            for (int j = 0; j < numItemMeasurements; j++) {
                int items = minItems + j * itemsStep;
                int sum = 0;
                for (int iteration = 0; iteration < iterations; iteration++) {
                    MaxAttractivityFeedbackGenerator generator = new MaxAttractivityFeedbackGenerator(pageSize, clicks, items, 1.0f);
                    FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
                    while (generator.hasNext()) {
                        consumer.addFeedBack(generator.next());
                    }
                    List<Integer> consumerSortedItems = generator.getItems();
                    consumer.sort(consumerSortedItems);
                    sum += SortDistance.distance(generator.getSortedItems(), consumerSortedItems);
                }
                int average = sum / iterations;
                // int random_distance = (int) (items * items / 3.0);
                float accuracy = (float) average / (items * items);
                measurements[i][j] = (int) (accuracy * 1000);
                System.err.println(String.format("clicks: %d, items: %d, accuracy: %s", clicks, items, accuracy));
            }
        }
        System.err.println(serializeToR(measurements));
        PrintWriter writer = new PrintWriter(new File("src/main/resources/reports/generated/html/AccuracyByClicksAndItems.html"), "UTF-8");
        writer.print(serializeToHtml(measurements));
        writer.flush();
        writer.close();
    }

    public static String serializeToR(float[][] measurement) {
        VelocityContext context = new VelocityContext();
        context.put("values", ReportUtils.matrixToString(measurement));
        context.put("cols", measurement[0].length);
        context.put("clicksStep", clicksStep);
        context.put("pageSize", pageSize);
        context.put("itemsStep", itemsStep);
        StringWriter w = new StringWriter();
        Template t = Context.getVelocityEngine().getTemplate("accuracyByClicksAndItems.vm");
        t.merge(context, w);
        return w.toString();
    }

    public static String serializeToHtml(float[][] measurement) {
        VelocityContext context = new VelocityContext();
        context.put("values", ReportUtils.matrixToJavascript(measurement));
        context.put("cols", measurement[0].length);
        context.put("clicksStep", clicksStep);
        context.put("pageSize", pageSize);
        context.put("itemsStep", itemsStep);
        StringWriter w = new StringWriter();
        Template t = Context.getVelocityEngine().getTemplate("reports/AccuracyByClicksAndItems.vm");
        t.merge(context, w);
        return w.toString();
    }


}

