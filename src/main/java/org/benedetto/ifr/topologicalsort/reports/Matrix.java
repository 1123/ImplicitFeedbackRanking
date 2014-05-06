package org.benedetto.ifr.topologicalsort.reports;

import java.util.List;

public class Matrix {
    private float [][] values;
    private List<String> rowNames;
    private List<String> columnNames;

    public List<String> getRowNames() {
        return rowNames;
    }

    public void setRowNames(List<String> rowNames) {
        this.rowNames = rowNames;
    }

    public float[][] getValues() {
        return values;
    }

    public void setValues(float[][] values) {
        this.values = values;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
