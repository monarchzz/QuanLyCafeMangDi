/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view.chart;

/**
 *
 * @author monar
 */
public class DatasetValue {
    private String series;
    private String category;
    private double value;

    public DatasetValue() {
    }

    public DatasetValue(String series, String category, double value) {
        this.series = series;
        this.category = category;
        this.value = value;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
}
