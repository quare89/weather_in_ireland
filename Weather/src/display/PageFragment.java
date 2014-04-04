package display;

import java.util.Date;

import src.weather.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dataManager.DataWeather;

public class PageFragment extends Fragment {

	   int id;
	   DataWeather dw;
	   TextView tv_date,tv_cond,tv_mintemp,tv_maxtemp,tv_wdir,tv_wspeed,tv_sunrise;
	   TextView tv_sunset,tv_pressure,tv_humidity,tv_visibility,tv_uvrisk,tv_update;
	   ImageView iv_cond,iv_wind;
	  
	   
	   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
	      
	      // fragment not when container null
	      if (container == null) {
	         return null;
	      }
	           
	      
	      Bundle myBundle;
	      myBundle=this.getArguments();
	      dw=(DataWeather)myBundle.getSerializable("data");
	      
	      
	      
	      // inflate view from layout
	      View view = (LinearLayout)inflater.inflate(R.layout.weather_sheet,container,false);
	      // update text
	      tv_date = (TextView) view.findViewById(R.id.tv_date);
	      tv_cond = (TextView) view.findViewById(R.id.tv_cond);
	      tv_mintemp = (TextView) view.findViewById(R.id.tv_mintemp);
	      tv_maxtemp = (TextView) view.findViewById(R.id.tv_maxtemp);
	      tv_wdir = (TextView) view.findViewById(R.id.tv_wdir);
	      tv_wspeed = (TextView) view.findViewById(R.id.tv_wspeed);
	      tv_sunrise = (TextView) view.findViewById(R.id.tv_sunrise);
	      tv_sunset = (TextView) view.findViewById(R.id.tv_sunset);
	      tv_pressure = (TextView) view.findViewById(R.id.tv_pressure);
	      tv_humidity = (TextView) view.findViewById(R.id.tv_humidity);
	      tv_visibility = (TextView) view.findViewById(R.id.tv_visibility);
	      tv_uvrisk = (TextView) view.findViewById(R.id.tv_uvrisk);
	      tv_update = (TextView) view.findViewById(R.id.tv_update);
	      
	      //date
	      Date d=dw.getDate_weather();
	      String[] weekDays={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	      String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	      
	      String date=weekDays[d.getDay()]+" "+d.getDate()+" "+months[d.getMonth()]+" "+(1900+d.getYear());
	      tv_date.setText(date);
	      
	      //conditions
	      if(dw.getCondition()!="")
	    	  tv_cond.setText(dw.getCondition());
	      else
	    	  notAval(tv_cond);
	      
	      //temp
	      if(dw.getMin_temp()!=100)
	      tv_mintemp.setText(dw.getMin_temp()+"°C");
	      else
	    	  notAval(tv_mintemp);
	      
	      if(dw.getMax_temp()!=100)
	      tv_maxtemp.setText(dw.getMax_temp()+"°C");
	      else
	    	  notAval(tv_maxtemp);
	      //wind
	      if(dw.getWind_dir()!="")
	      tv_wdir.setText(dw.getWind_dir());
	      else
	    	  notAval(tv_wdir);
	      
	      if(dw.getWind_speed()!=0)
	      tv_wspeed.setText(dw.getWind_speed()+"mph");
	      else
	    	  notAval(tv_wspeed);
	      //sun
	      if(dw.getSunrise()!="")
	      tv_sunrise.setText(dw.getSunrise());
	      else
	    	  notAval(tv_sunrise);
	      
	      if(dw.getSunset()!="")
	      tv_sunset.setText(dw.getSunset());
	      else
	    	  notAval(tv_sunset);
	      
	      //pressure,humidity,visibility,uvrisk
	      if(dw.getPressure()!=-1)
	      tv_pressure.setText(dw.getPressure()+"mbar");
	      else
	    	  notAval(tv_pressure);
	      
	      if(dw.getHumidity()!=-1)
	      tv_humidity.setText(dw.getHumidity()+"%");
	      else
	    	  notAval(tv_humidity);
	      
	      if(dw.getVisibility()!="")
	      tv_visibility.setText(dw.getVisibility());
	      else
	    	  notAval(tv_visibility);
	      
	      if(dw.getUv_risk()!=-1)
	      tv_uvrisk.setText(String.valueOf(dw.getUv_risk()));
	      else
	    	  notAval(tv_uvrisk);
	      
	      
	      String timeDate=dw.getPub_date().toGMTString();
	      timeDate=timeDate.substring(0,timeDate.length()-7);
	      
	      tv_update.setText("last update: "+timeDate);
	      
	      //ImageView
	      iv_wind=(ImageView) view.findViewById(R.id.iv_wind);
	      iv_cond=(ImageView) view.findViewById(R.id.iv_cond);
	      
	      //wind
	     chooseImageWind(view,iv_wind,dw.getWind_dir());
	     //cond
	     chooseImageCond(view,iv_cond,dw.getCondition());
	      
	      return view;
	   }
	    
	  
	private void notAval(TextView tv) {
		tv.setText("N/A");
		
	}


	private void chooseImageWind(View v,ImageView w,String dir)
	   {
		Drawable d;
	   
		try{
		   d=(Drawable)v.getResources().getDrawable(v.getResources().getIdentifier("wind_"+dir.toLowerCase(), "drawable", "src.weather"));
		}catch(Exception e){d=v.getResources().getDrawable(R.drawable.cond_100);}
		   w.setImageDrawable(d);
	   }
	   
	   private void chooseImageCond(View v,ImageView w,String cond)
	   {
		   String[] conditions={"Clear Sky","Sunny","Partly cloudy","Sunny Intervals",
				   "Sandstorm","Mist","Fog","White Cloud","Grey Cloud",
				   "Light Rain Shower","Drizzle","Light Rain","Heavy Rain Shower",
				   "Heavy Rain","Sleet Shower","Sleet","Hail Shower","Hail",
				   "Light Snow Shower","Light Snow","Heavy Snow Shower","Heavy Snow",
				   "Thundery Shower","Thunder Storm","Tornado","Hazy"};
		   
		   int imgId=100;
		   
		   for(int i=0;i<conditions.length;i++)
		   {
			   if(conditions[i].equalsIgnoreCase(cond)) 
			   {
				   imgId=i;
				   break; 
			   }
			   
		   }
		   
		  
		   
		   Drawable d=(Drawable)v.getResources().getDrawable(
				   v.getResources().getIdentifier(
						   "cond_"+imgId, "drawable", "src.weather"
						   )
					);
		   
		   w.setImageDrawable(d);
	   }
	}
