package org.benedetto.ifr.topologicalsort.reports;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.feedback.MaxAttractivityFeedbackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.io.StringWriter;

public class TimeByItemsAndClicks {

    static final int pageSize = 10;
    static final float randomness = 1.0f;
    static final int iterations = 10;
    static final int minClicks = 50;
    static final int clicksStep = 300;
    static final int clickMeasurements = 10;
    static final int minItems = 100;
    static final int itemsStep = 30;
    static final int itemMeasurements = 20;

    public static void main(String[] args) throws InvalidExpansionStateException, InvalidAhrszStateException {
        // warm up
        float runtime[][] = measure();
        runtime = measure();
        System.err.println(searializeResults(runtime));
    }

    // untested
    private static float[][] measure() throws InvalidExpansionStateException, InvalidAhrszStateException {
        float [][] runtime = new float[clickMeasurements][itemMeasurements];
        for (int c = 0; c < clickMeasurements; c++) {
            int clicks = minClicks + c * clicksStep;
            for (int i = 0; i < itemMeasurements; i++) {
                int items = minItems + i * itemsStep;
                long durationSum = 0;
                for (int iteration = 0; iteration < iterations; iteration++) {
                    MaxAttractivityFeedbackGenerator generator =
                            new MaxAttractivityFeedbackGenerator(pageSize, clicks, items, randomness);
                    FeedBackConsumer<Integer> feedBackConsumer = new AhrszFeedBackConsumer<>();
                    long start = System.currentTimeMillis();
                    while (generator.hasNext()) feedBackConsumer.addFeedBack(generator.next());
                    durationSum += System.currentTimeMillis() - start;
                }
                runtime[c][i] = (float) ( ( durationSum + 1.0 ) / iterations);
                System.err.println(String.format("clicks: %d, items: %d, runtime %f", clicks, items, runtime[c][i]));
            }
        }
        return runtime;
    }

    public static String searializeResults(float [][] measurement) {
        VelocityContext context = new VelocityContext();
        context.put("values", ReportUtils.matrixToString(measurement));
        context.put("cols", measurement[0].length);
        context.put("clicksStep", clicksStep);
        context.put("pageSize", pageSize);
        context.put("itemsStep", itemsStep);
        StringWriter w = new StringWriter();
        Template t = Context.getVelocityEngine().getTemplate("timeByItemsAndClicks.vm");
        t.merge(context, w);
        return w.toString();
    }
}
