package org.benedetto.ifr.topologicalsort.reports;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import org.apache.hadoop.util.StringUtils;
import org.benedetto.ifr.feedback.generators.MaxAttractivityFeedbackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.util.ArrayList;
import java.util.List;

public class TimeByClicks {

    static final int pageSize = 5;
    static final int items = 100;
    static final float randomness = 1.0f;
    static final int iterations = 5;
    static final int clicksStep = 150;
    static final int maxClicks = 1500;
    static final int minClicks = 50;

    public static void main( String args [] ) throws InvalidExpansionStateException, InvalidAhrszStateException {

        // warmUp
        TimeByClicks timebyClicks = new TimeByClicks();
        timebyClicks.measure();
        Results results = timebyClicks.measure();
        System.err.println(results.serialize());
    }

    private Results measure() throws InvalidExpansionStateException, InvalidAhrszStateException {
        List<Integer> xCoordinates = new ArrayList<>();
        List<Integer> yCoordinates = new ArrayList<>();
        for (int clicks = minClicks; clicks < maxClicks; clicks += clicksStep) {
            long diff = 0;
            for (int iteration = 0; iteration < iterations; iteration++) {
                MaxAttractivityFeedbackGenerator generator =
                        new MaxAttractivityFeedbackGenerator(pageSize, clicks, items, randomness);
                long start = System.currentTimeMillis();
                MeasureUtils.consume(generator);
                long end = System.currentTimeMillis();
                diff += (end - start);
            }
            float averageDiff = diff / iterations;
            System.err.println(clicks + "; " + averageDiff);
            xCoordinates.add(clicks);
            yCoordinates.add((int) averageDiff);
        }
        return new Results(xCoordinates, yCoordinates);
    }

    class Results {

        List<Integer> xCoordinates;
        List<Integer> yCoordinates;

        public Results(List<Integer> xCoordinates, List<Integer> yCoordinates) {
            this.xCoordinates = xCoordinates;
            this.yCoordinates = yCoordinates;
        }

        private String serializeListAsRDataFrame(List<Integer> values) {
            List<String> stringValues = Lists.transform(values, Functions.toStringFunction());
            return String.format("c(%s)", StringUtils.join(", ", stringValues));
        }

        public String serialize() {
            String result = "";
            result += String.format(
                    "clicks = %s %s", this.serializeListAsRDataFrame(this.xCoordinates),
                    System.getProperty("line.separator")
            );
            result += String.format(
                    "runTime = %s %s", this.serializeListAsRDataFrame(this.yCoordinates),
                    System.getProperty("line.separator")
            );
            return result;
        }

    }


}
