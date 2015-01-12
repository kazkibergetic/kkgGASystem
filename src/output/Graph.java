package output;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * -------------------------
 * TimeSeriesChartDemo1.java
 * -------------------------
 * (C) Copyright 2003-2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1, copied from the demo collection that ships with
 *               the JFreeChart Developer Guide (DG);
 *
 */
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import params.Parameters;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class Graph extends ApplicationFrame {

	
	ArrayList<Double> bestFitness;
	ArrayList<Double> averageFitness;
	static int counter=0;
	
    private static final long serialVersionUID = 1L;

    {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    /**
     * A demonstration application showing how to create a simple time series
     * chart. This example uses monthly data.
     *
     * @param title the frame title.
     */
    public Graph(String title) {
        super(title);
        bestFitness = new ArrayList<Double> ();
        averageFitness = new ArrayList<Double> ();
        this.initializeDefault();
        
       
    }

    
    private void initializeDefault()
    {
    	for(int i=0; i<Parameters.getNumberOfGenerations(); i++)
    	{
    		bestFitness.add(0d);
    		averageFitness.add(0d);
    	}
    }
    
    public void addValues(int generation, double average, double best)
    {
    	counter++;
    	int g = generation-1;
    
    	bestFitness.set(g, bestFitness.get(g)+best);
    	averageFitness.set(g, averageFitness.get(g)+average);
    	
    	
    }
    /**
     * Creates a chart.
     *
     * @param dataset a dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Fitness over Generations (average of " + Parameters.getNumberOfRuns() + " runs)", "Generations", "Fitness", dataset);
        
      
        chart.setBackgroundPaint(Color.white);
      
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        
        
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            
            
        
            renderer.setDrawSeriesLineAsPath(true);
            
            renderer.setSeriesLinesVisible(0, true);
            renderer.setSeriesShapesVisible(0, false);
            
             renderer.setSeriesLinesVisible(1, true);
            renderer.setSeriesShapesVisible(1, false);
            plot.setRenderer(renderer);
        }


      //  NumberAxis domain = (NumberAxis) plot.getDomainAxis();
      //  domain.setTickUnit(new NumberTickUnit(10));
        //  axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private XYDataset createDataset() throws FileNotFoundException {



        XYSeries best = new XYSeries("The best fitness of the entire population for this generation");

        XYSeries average = new XYSeries("The mean fitness of the entire population for this generation");

        
        
        
     
 
        
        
        for (int i = 0; i < Parameters.getNumberOfGenerations(); i++) {
            average.add(i, (double)  averageFitness.get(i) / Parameters.getNumberOfRuns());
            best.add(i, (double) bestFitness.get(i) / Parameters.getNumberOfRuns());

        }


        //  s1.add(new Month(2, 2001), 181.8);


        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(average);
        dataset.addSeries(best);

        return dataset;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
   /* public static JPanel createDemoPanel(int r) throws FileNotFoundException {
        JFreeChart chart = createChart(createDataset(r));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }
*/
    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public void start(String folderName) throws FileNotFoundException, IOException {

    	
    	if(counter != (Parameters.getNumberOfRuns()*Parameters.getNumberOfGenerations()))
    	{
    		System.out.println("ERROR "+counter);
    		
    	}
       // Graph g = new Graph("text", Output.bestRun);
      //  g.pack();
      //  RefineryUtilities.centerFrameOnScreen(g);
      //  g.setVisible(true);
        JFreeChart chart = createChart(createDataset());
        //ChartUtilities.writeChartAsPNG(g,createDataset(1), 400,200);
        String mresultFile;
        mresultFile = folderName+"/graph.jpeg";
        File targetFile = new File(mresultFile);
        ChartUtilities.saveChartAsJPEG(targetFile, chart, 600, 400);

    }

   
}
