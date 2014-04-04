package graphManager;


import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.androidplot.Plot;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeriesFormatter;
import com.androidplot.xy.XYStepMode;

public class Graph {
	
	private Number[] x;
	private Number[] y;
	private XYPlot mySimpleXYPlot;
	private String title;
	private String form;
	private int lb;
	private int ub;
	private XYSeriesFormatter<?> seriesFormatter;
	private String xlabel;
	private String ylabel;
	
	public Graph(Number[] x, Number[] y, XYPlot graph,String title,String format,int lowerBound,int upperBound,XYSeriesFormatter<?> seriesFormatter,String xlab,String ylab)
	{
		this.x=x;
		this.y=y;
		this.mySimpleXYPlot=graph;
		this.title=title;
		this.form=format;
		this.lb=lowerBound;
		this.ub=upperBound;
		this.seriesFormatter=seriesFormatter;
		this.xlabel=xlab;
		this.ylabel=ylab;
	}
	
	
	public void drawGraph()
	{
	
		
		
		// create our series from our array of nums:
		XYSeries series2 = new SimpleXYSeries(
    		y,
            x,
            title);

		mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
		mySimpleXYPlot.getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
		mySimpleXYPlot.getGraphWidget().getGridLinePaint().setPathEffect(new DashPathEffect(new float[]{1,1}, 1));
		mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
		mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
	
	    mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
	    mySimpleXYPlot.getBorderPaint().setStrokeWidth(1);
	    mySimpleXYPlot.getBorderPaint().setAntiAlias(false);
	    mySimpleXYPlot.getBorderPaint().setColor(Color.WHITE);
	
	    	    
	    mySimpleXYPlot.addSeries(series2, seriesFormatter);
		
	
	    // draw a domain tick for each year:
	    mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, y.length);
	    mySimpleXYPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL,1);
	
	    // customize our domain/range labels
	    mySimpleXYPlot.setDomainLabel(xlabel);
	    mySimpleXYPlot.setRangeLabel(ylabel);
	
	    // get rid of decimal points in our range labels:
	    mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
	    
	    
	    mySimpleXYPlot.setRangeLowerBoundary(lb,BoundaryMode.FIXED);
	    mySimpleXYPlot.setRangeUpperBoundary(ub,BoundaryMode.FIXED);
	    
	    mySimpleXYPlot.setTicksPerRangeLabel(5);
        
	    
	    mySimpleXYPlot.setDomainValueFormat(new Format() 
	    	{
	        
				private static final long serialVersionUID = 1L;
			// create a simple date format that draws on the year portion of our timestamp.
	        // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
	        // for a full description of SimpleDateFormat.
	        private SimpleDateFormat dateFormat = new SimpleDateFormat(form);
	        private String prec="";
	        @Override
	        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
	
	            long timestamp = ((Number) obj).longValue();
	            Date date = new Date(timestamp);
	            StringBuffer res=new StringBuffer("");
	            if(!dateFormat.format(date).equalsIgnoreCase(prec))
	            {
	            	res=dateFormat.format(date, toAppendTo, pos);
	            	prec=res.toString();
	            }
	            else
	            	toAppendTo.append("");
	            
	            
	            return res;
	        }
	
	        @Override
	        public Object parseObject(String source, ParsePosition pos) {
	            return null;
	
	        }
	    });
	
	    // by default, AndroidPlot displays developer guides to aid in laying out your plot.
	    // To get rid of them call disableAllMarkup():
	    mySimpleXYPlot.disableAllMarkup();
	}
}
