package dataManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;

public class DataWeatherExtractor {
	

	private DbConnection db;
	private City city;
	private DataWeather[] dw;
	
	public DataWeatherExtractor(DbConnection db, City city) {
		
		this.db = db;
		this.city = city;
	}
	
	@SuppressLint("SimpleDateFormat")
	public void extractFromDb(String whereClause)
	{
		
		ArrayList<DataWeather> listdw=new ArrayList<DataWeather>();
		
		db.openDatabase();
			
		ArrayList<String> list=db.selectDatabase("*", "Data", whereClause);
		
		db.closeDatabase();
				
		
		 
		Date date_weather=null; 
		Date pub_date=null;
		String condition=""; 
		int min_temp=100; 
		int max_temp=100; 
		String wind_dir="";
		int wind_speed=0; 
		String visibility=""; 
		int pressure=-1; 
		int humidity=-1;
		String sunset=""; 
		String sunrise=""; 
		int uv_risk=-1;
		
		for(int i=0;i<list.size();i++)
		{
			//data formatted by ";;"
			String[] features=list.get(i).split(";;");
			
			//date_weather
			try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			date_weather = formatter.parse(features[0]);
			} catch (Exception e) {}
				
			
			//pub_date
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				pub_date = formatter.parse(features[1].substring(0, features[1].length()-2));
				} catch (Exception e) {}
				
			
			
			//condition
			if(features[2]!=null && features[2]!="")
				condition=features[2];
		
			
			//min and max temp
			try{
			min_temp=Integer.parseInt(features[3]);
			} catch (Exception e) {}
			
			try{
			max_temp=Integer.parseInt(features[4]);
			} catch (Exception e) {}		
			
			
			//wind_dir
			if(features[5]!=null && features[5]!="")
			{
				String[] temp=features[5].split(" ");
				wind_dir="";
				for (int j=0;j<temp.length;j++)
				{
					wind_dir+=temp[j].charAt(0);
				}
			}
			
			//wind_speed
			try {
			wind_speed=Integer.parseInt(features[6]);
			
			//visibility
			if(features[7]!=null && features[7]!="")
				visibility=features[7];
			} catch (Exception e) {}
			
			//pressure
			try {
			pressure=Integer.parseInt(features[8]);
			} catch (Exception e) {}
			
			//humidity
			try {
			humidity=Integer.parseInt(features[9]);
			} catch (Exception e) {}
			//sunset and sunrise
			if(features[10]!=null && features[10]!="")
				sunset=features[10];
			if(features[11]!=null && features[11]!="")
				sunrise=features[11];
			
			//uv_risk
			try {
			uv_risk=Integer.parseInt(features[12]);
			} catch (Exception e) {}
			
			
			listdw.add(new DataWeather(city,date_weather,pub_date,condition,min_temp,max_temp,wind_dir,
					wind_speed,visibility,pressure,humidity,sunset,sunrise,uv_risk));
			
		}
		
		dw=new DataWeather[listdw.size()];
		for(int i=0;i<listdw.size();i++)
		{
			dw[i]=listdw.get(i);
		}
		
		
	}

	/**
	 * @return the dw
	 */
	
	public DataWeather[] getDw() {
		return dw;
	}
	
	
	

}
