package org.benedetto.ifr.topologicalsort.reports;

import com.google.gson.Gson;
import org.apache.hadoop.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportUtils {

    public static String matrixToString(float [][] measurement) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < measurement.length; i++) {
            List<String> elements = new ArrayList<>();
            for (int j = 0; j < measurement[0].length; j++) {
                elements.add(measurement[i][j] + "");
            }
            lines.add(StringUtils.join(",", elements));
        }
        return StringUtils.join(", " + System.getProperty("line.separator"), lines);
    }

    public static String matrixToJavascript(float[][] measurement) {
        Gson gson = new Gson();
        return gson.toJson(measurement);
    }

}
