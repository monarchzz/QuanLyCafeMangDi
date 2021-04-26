/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view.chart;

import java.awt.Color;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author monar
 */
public class ChartAdapter{
    
    private List<DatasetValue> dsDoanhThu;
    private List<DatasetValue> dsChiPhi;
    private List<DatasetValue> dsLoiNhuan;

    public ChartAdapter(List<DatasetValue> dsDoanhThu, List<DatasetValue> dsChiPhi, List<DatasetValue> dsLoiNhuan) {
        this.dsDoanhThu = dsDoanhThu;
        this.dsChiPhi = dsChiPhi;
        this.dsLoiNhuan = dsLoiNhuan;
    }
    

    public JFreeChart getChart(String category){
        
        final CategoryDataset dataset1 = createDataset1();
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Xu hướng",        // chart title
            category,               // domain axis label
            "Doanh thu",                  // range axis label
            dataset1,                 // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URL generator?  Not required...
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
//        chart.getLegend().setAnchor(Legend.SOUTH);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        final CategoryDataset dataset2 = createDataset2();
        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);

//        final CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        final ValueAxis axis2 = new NumberAxis("Lợi nhuận");
        plot.setRangeAxis(1, axis2);

        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(1, renderer2);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        
        return chart;
    }
    
    private CategoryDataset createDataset1() {

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (DatasetValue data : dsDoanhThu){
            dataset.addValue(data.getValue(), data.getSeries(), data.getCategory());
        }
        
        for (DatasetValue data : dsChiPhi){
            dataset.addValue(data.getValue(), data.getSeries(), data.getCategory());
        }

        return dataset;

    }

    private CategoryDataset createDataset2() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (DatasetValue data : dsLoiNhuan){
            dataset.addValue(data.getValue(), data.getSeries(), data.getCategory());
        }

        return dataset;

    }
}
