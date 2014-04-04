package graphManager;

import com.androidplot.series.XYSeries;

public class SimpleXYSeries implements XYSeries {
   
    private Number[] x;
    private Number[] y;
    private String title;
    
    public SimpleXYSeries(Number[] x,Number[] y,String title)
    {
    	this.x=x;
    	this.y=y;
    	this.title=title;
    }
    
    // f(x) = x
    @Override
    public Number getX(int index) {
        
    	return x[index];
    }

    @Override
    public String getTitle() {
        return title;
    }

    // range consists of all the values in vals
    @Override
    public int size() {
        return y.length;
    }

    public void onReadBegin() {

    }

    public void onReadEnd() {

    }

    // return vals[index]
    @Override
    public Number getY(int index) {
        
        return y[index];
    }

}