package src.weather;

import graphManager.Graph;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.StepFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeriesFormatter;

import dataManager.City;
import dataManager.DataWeather;
import dataManager.DataWeatherExtractor;
import dataManager.DbConnection;

public class GraphActivity extends Activity implements OnClickListener{

	 private XYPlot mySimpleXYPlot;
	 
	//the considered city
		City city;
		int type;
		
		Bundle myBundle;
		TextView tv_city;
		
		
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		
		
		Intent myLocalIntent=getIntent();
        
        //extract the city
        myLocalIntent=getIntent();
        myBundle=myLocalIntent.getExtras();
        city=(City)myBundle.getSerializable("city");
        type=myBundle.getInt("type");
		
       
        
      //find textviews
        tv_city=(TextView) findViewById(R.id.tv_city);
        
        tv_city.setText(city.getName());
        
        String whereClause="";
        
        switch(type)
        {
        case 1: whereClause="city="+city.getId()+"  ORDER BY Date_weather";break;
        case 2: whereClause="city="+city.getId()+" AND strftime('%Y',Date_weather)=strftime('%Y','now') ORDER BY Date_weather";break;
        case 3: whereClause="city="+city.getId()+" AND strftime('%m',Date_weather)=strftime('%m','now') ORDER BY Date_weather";break;
        case 4: whereClause="city="+city.getId()+"  ORDER BY Date_weather";break;
        case 5: whereClause="city="+city.getId()+"  ORDER BY Date_weather";break;
       
        }
        

		String path=Environment.getExternalStorageDirectory().getPath()+"/dbWeather.sqlite";
        DbConnection db=new DbConnection(this,path);
        
        DataWeatherExtractor dw_extr=new DataWeatherExtractor(db,city);
        dw_extr.extractFromDb(whereClause);
        DataWeather[] dws=dw_extr.getDw();
        
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        
        Number[] x = new Number[dws.length];
        Number[] y=new Number[dws.length];
        
        XYSeriesFormatter<?> seriesFormatter=null;
        
     // setup our line fill paint to be a slightly transparent gradient:
	    Paint lineFill = new Paint();
	    lineFill.setAlpha(200);
	    lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR));
	
	    //stepFormatter
        StepFormatter stepFormatter  = new StepFormatter(Color.rgb(0, 0,0), Color.BLUE);
	    stepFormatter.getLinePaint().setStrokeWidth(1);
	
	    stepFormatter.getLinePaint().setAntiAlias(false);
	    stepFormatter.setFillPaint(lineFill);
        
        
	    LineAndPointFormatter lineFormatter  = new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED);
        lineFormatter.setFillPaint(lineFill);
        
        
        String title="",format="",xlab="Time",ylab="";
        int lb=0,ub=0;
        
        //creation of series
        if(type==1)
        {
        	x=generate_dates(dws);
        	y=generate_temperature(dws);
        	title="Total data temperature";
        	format="MM-yy";
        	lb=-7;
        	ub=25;
        	seriesFormatter=lineFormatter;
        	ylab="Temp";
        }
        if(type==2)
        {
        	x=generate_dates(dws);
        	y=generate_temperature(dws);
        	title="Year temperature";
        	format="MMM";
        	lb=-7;
        	ub=25;
        	seriesFormatter=lineFormatter;
        	ylab="Temp";
        }
        if(type==3)
        {
        	x=generate_dates(dws);
        	y=generate_temperature(dws);
        	title="Month temperature";
        	format="dd";
        	lb=-7;
        	ub=25;
        	seriesFormatter=stepFormatter;
        	ylab="Temp";
        }
        if(type==4)
        {
        	x=generate_dates(dws);
        	y=generate_humidity(dws);
        	title="Total data Humidity";
        	format="MM-yy";
        	lb=0;
        	ub=100;
        	seriesFormatter=lineFormatter;
        	ylab="Humidity";
        }
        if(type==5)
        {
        	x=generate_dates(dws);
        	y=generate_pressure(dws);
        	title="Total data Pressure";
        	format="MM-yy";
        	lb=980;
        	ub=1020;
        	seriesFormatter=lineFormatter;
        	ylab="Pressure";
        }
       
        	
        TextView tv_grtitle=(TextView) findViewById(R.id.tv_graph_title);
        tv_grtitle.setText(title);
        
        if(dws.length>0)
        {
        	Graph g;
        	g=new Graph(y,x,mySimpleXYPlot,title,format,lb,ub,seriesFormatter,xlab,ylab);
        
        	g.drawGraph();
        }
        else
        {
        	
        	Toast.makeText(getBaseContext(), "No data in the database: grab data and try again",Toast.LENGTH_LONG).show();
        }
        
        ImageButton back=(ImageButton) findViewById(R.id.b_back);
        back.setOnClickListener(this);
       
       
	}

	private Number[] generate_pressure(DataWeather[] dws) {
		
		Number[] res=new Number[dws.length];
				
				for(int i=0;i<dws.length;i++)
		        {
					Number temp;
					if(dws[i].getPressure()==-1)
					{
						try{
							temp=res[i-1];
						}
						catch(Exception e)
						{
							temp=1000;
						}
					}
			    	else
			    		temp=dws[i].getPressure();
			    	
			    	res[i]=temp;
		}
				
				return res;
	}

	private Number[] generate_humidity(DataWeather[] dws) {
		
Number[] res=new Number[dws.length];
		
		for(int i=0;i<dws.length;i++)
        {
			Number temp;
			if(dws[i].getHumidity()==-1)
			{
				try{
					temp=res[i-1];
				}
				catch(Exception e)
				{
					temp=70;
				}
			}
	    	else
	    		temp=dws[i].getHumidity();
	    	
	    	res[i]=temp;
}
		
		return res;
	}

	
	private Number[] generate_temperature(DataWeather[] dws) {
		Number[] res=new Number[dws.length];
		
		for(int i=0;i<dws.length;i++)
        {
			Number temp;
			if(dws[i].getMax_temp()==100)
	    		temp=(double)dws[i].getMin_temp();
	    	else if (dws[i].getMin_temp()==100)
	    		temp=(double)dws[i].getMax_temp();
	    	else
	    		temp=(double)(dws[i].getMax_temp()+dws[i].getMin_temp())/2;
	    	
	    	res[i]=temp;
        }
		
		return res;
	}

	private Number[] generate_dates(DataWeather[] dws) {
		
		Number[] res=new Number[dws.length];
		
		for(int i=0;i<dws.length;i++)
        {
        	Date date=dws[i].getDate_weather();
        	
        	Number temp=(Number)date.getTime();
        	
        	res[i]=temp;
        }
		
		return res;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_graph, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

	
	
}
