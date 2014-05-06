package org.benedetto.ifr.topologicalsort.reports;

import org.apache.hadoop.util.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TimeByClicksAndPageSize {

    static class Plotconfiguration {

        int items;
        float randomness;
        int iterations;
        int clicksStep;
        int clickMeasurements;
        int minClicks;
        int minPageSize;
        int pageMeasurements;
        int pageSizeStep;

        public Plotconfiguration(
                int items, float randomness, int iterations, int clicksStep, int clickMeasurements,
                int minClicks, int minPageSize, int pageMeasurements, int pageSizeStep) {
            this.items = items;
            this.randomness = randomness;
            this.iterations = iterations;
            this.clicksStep = clicksStep;
            this.clickMeasurements = clickMeasurements;
            this.minClicks = minClicks;
            this.minPageSize = minPageSize;
            this.pageMeasurements = pageMeasurements;
            this.pageSizeStep = pageSizeStep;
        }

    }

    public static void main(String [] args) throws InvalidExpansionStateException, InvalidAhrszStateException {
        Plotconfiguration plotconfiguration = new Plotconfiguration(200, 1.0f, 10, 300, 10, 100, 3, 10, 3);
        // warmup
        Matrix result = measureAll(plotconfiguration);
        result = measureAll(plotconfiguration);
        System.err.println(generateDiagram(result, plotconfiguration));
    }

    private static Matrix measureAll(Plotconfiguration plotconfiguration) throws InvalidExpansionStateException, InvalidAhrszStateException {
        float [][] runTime = new float[plotconfiguration.clickMeasurements][plotconfiguration.pageMeasurements];
        for (int clickMeasurement = 0; clickMeasurement < plotconfiguration.clickMeasurements; clickMeasurement++) {
            int clicks = plotconfiguration.minClicks + clickMeasurement * plotconfiguration.clicksStep;
            for (int pageMeasurement = 0; pageMeasurement < plotconfiguration.pageMeasurements; pageMeasurement++) {
                int pageSize = plotconfiguration.minPageSize + pageMeasurement * plotconfiguration.pageSizeStep;
                AhrszParams ahrszParams = new AhrszParams(plotconfiguration.items, plotconfiguration.randomness, plotconfiguration.iterations, clicks, pageSize);
                float duration = MeasureUtils.measure(ahrszParams);
                runTime[clickMeasurement][pageMeasurement] = duration;
                System.err.println(String.format("clicks: %d, pageSize %d, duration: %s", clicks, pageSize, duration));
            }
        }
        Matrix result = new Matrix();
        result.setValues(runTime);
        result.setColumnNames(getColumnNames(plotconfiguration));
        result.setRowNames(getRowNames(plotconfiguration));
        return result;
    }

    private static List<String> getColumnNames(Plotconfiguration plotconfiguration) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < plotconfiguration.pageMeasurements; i++) {
            int pageSize = plotconfiguration.minPageSize + i * plotconfiguration.pageSizeStep;
            result.add(pageSize + "");
        }
        return result;
    }

    private static List<String> getRowNames(Plotconfiguration plotconfiguration) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < plotconfiguration.clickMeasurements; i++) {
            int pageSize = plotconfiguration.minClicks + i * plotconfiguration.clicksStep;
            result.add(pageSize + "");
        }
        return result;
    }

    public static String generateDiagram(Matrix measurement, Plotconfiguration plotconfiguration) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < measurement.getValues().length; i++) {
            for (int j = 0; j < measurement.getValues()[0].length; j++) {
                elements.add(measurement.getValues()[i][j] + "");
            }
        }
        VelocityContext context = new VelocityContext();
        context.put("values", StringUtils.join(", ", elements));
        context.put("cols", measurement.getValues()[0].length);
        context.put("colnames", StringUtils.join(", ", measurement.getColumnNames()));
        context.put("rownames", StringUtils.join(", ", measurement.getRowNames()));
        context.put("clicksStep", plotconfiguration.clicksStep);
        context.put("items", plotconfiguration.items);
        context.put("pageSizeStep", plotconfiguration.pageSizeStep);
        StringWriter w = new StringWriter();
        Template t = Context.getVelocityEngine().getTemplate("timeByClicksAndPageSize.vm");
        t.merge(context, w);
        return w.toString();
    }

}
