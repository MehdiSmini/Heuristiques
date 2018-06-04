
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChartDemo extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChartDemo(String title, int [] sample_array, int smaple_nummber ) {
        super(title);

        CategoryDataset dataset = createDataset(sample_array,smaple_nummber);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setBackground(null);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDismissDelay(Integer.MAX_VALUE);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static CategoryDataset createDataset(int [] data,int data_nummber) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int cat1,cat2,cat3,cat4,cat5,cat6;
        cat1=cat2=cat3=cat4=cat5=cat6=0;
        for (int i=0;i<data_nummber;i++) {
             if      (data[i] > 1400)                     { cat1++; dataset.addValue(cat1, "", " more than 1400 ms");}
             else if (data[i] >= 1351 && data[i] <= 1399) { cat2++; dataset.addValue(cat2, "", " 1351-1399 ms "); }
             else if (data[i] >= 1300 && data[i] <= 1350) { cat3++; dataset.addValue(cat3, "", " 1300-1350 ms "); }
             else if (data[i] >= 1251 && data[i] <= 1299) { cat4++; dataset.addValue(cat4, "", "1251-1299 ms "); }
             else if (data[i] >= 1200 && data[i] <= 1250) { cat5 ++; dataset.addValue(cat5, "", "1200-1250 ms "); }
             else if (data[i] < 1200)                     { cat6 ++; dataset.addValue(cat6, "", "less than 1200 ms "); }
            }

        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogramme  de solution", null /* x-axis label*/,
                "Nombre de ration" /* y-axis label */, dataset);
        chart.addSubtitle(new TextTitle(""));
        chart.setBackgroundPaint(null);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(null);

        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
   /* public static void main(String[] args) {
        BarChartDemo demo = new BarChartDemo("JFreeChart: BarChartDemo1.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }*/

}
