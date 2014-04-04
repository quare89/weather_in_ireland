package dataManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;


public class GrabThread extends Thread{
	
	private ProgressDialog pd;
	private City city;
	private DbConnection db;
	private Timestamp initial_day=null;
	
	public GrabThread(ProgressDialog _pd,City _city,DbConnection _db)
	{
		pd=_pd;
		city=_city;
		db=_db;
	}
	
	
	public void run() {
		try{
					
			// setting & opening the connection using urlAddress
			URL url = new URL("http://open.live.bbc.co.uk/weather/feeds/en/"+city.getCod_rss()+"/3dayforecast.rss");
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) 
			{
				// USE httpConnection to get XML and make a document  
				// holding the parse tree constructed from the input
				InputStream in = httpConnection.getInputStream();
				
							
				// define a document builder to act on incoming stream
				DocumentBuilder db = DocumentBuilderFactory.newInstance()
				      .newDocumentBuilder();
				// make XML parse tree for incoming RSS stream
				Document dom = db.parse(in);
				// define access nodes in the parse tree
				Element docEle = dom.getDocumentElement();
				// look for individual news ("items" in this case)
				// put items in a NodeList collection (nl)
				NodeList nl = docEle.getElementsByTagName("item");
			
				if ((nl != null) && (nl.getLength() > 0)) 
				{
					for (int i = 0; i < nl.getLength(); i++) 
					{
						
						extractInfo(nl,i);
					}// for
				}// if
			}
			else
			{
				System.out.println("Connection problem");
			}
		
		} catch (Exception e) {
			System.out.println(e);
			
		}
		finally{
			db.closeDatabase();
			
		}

	// dismiss the progress dialog

	pd.dismiss();

	}
	
	private int extractInfo(NodeList ln,int i)
	{
		Node n=ln.item(i);
		String title,description,pubDate;
		NodeList cl=n.getChildNodes();
		
		//sections in <item>
		title=cl.item(1).getTextContent();
		description=cl.item(5).getTextContent();
		pubDate=cl.item(7).getTextContent();
		
		
		
		ArrayList<String> fields=new ArrayList<String>();
		ArrayList<String> argvs=new ArrayList<String>();
		
		fields.add("city");
		argvs.add(String.valueOf(city.getId()));
		
		//initialisation of the variables to put in the db
		String conditions="",min_temp="",max_temp="";
		String date_str="";
		String pub_date_str="";
		String wind_dir="",wind_speed="",visibility="",pressure="";
		String humidity="",uv_risk="",sunrise="",sunset="";
		
		//title splits
		try{
			String temp[]=title.split(",");
			
			
			conditions=temp[0].split(":")[1].trim();
			
			fields.add("conditions");
			argvs.add("'"+conditions+"'");
			
		}
		catch(Exception e){
			System.err.println("Error in day-conditions download");
			return -1;
			}
		
		//desc splits
		try{
		String temp[]=description.split(",");
		
		for(String t:temp)
		{
			if(t.contains("Maximum Temperature"))
			{
				max_temp=t.split(": ")[1].split("°C")[0];
				fields.add("max_temp");
				argvs.add(max_temp);
			}
			if(t.contains("Minimum Temperature"))
			{
				min_temp=t.split(": ")[1].split("°C")[0];
				fields.add("min_temp");
				argvs.add(min_temp);
			}
			if(t.contains("Wind Direction"))
			{
				wind_dir=t.split(": ")[1];
				fields.add("wind_dir");
				argvs.add("'"+wind_dir+"'");
			}
			if(t.contains("Wind Speed"))
			{
				wind_speed=t.split(": ")[1].split("mph")[0];
				fields.add("wind_speed");
				argvs.add(wind_speed);
			}
			if(t.contains("Visibility"))
			{
				visibility=t.split(": ")[1];
				fields.add("visibility");
				argvs.add("'"+visibility+"'");
			}
			if(t.contains("Pressure"))
			{
				pressure=t.split(": ")[1].split("mb")[0];
				fields.add("pressure");
				argvs.add(pressure);
			}
			if(t.contains("Humidity"))
			{
				humidity=t.split(": ")[1].split("%")[0];
				fields.add("humidity");
				argvs.add(humidity);
			}
			if(t.contains("UV Risk"))
			{
				uv_risk=t.split(": ")[1];
				fields.add("uv_risk");
				argvs.add(uv_risk);
			}
			if(t.contains("Sunrise"))
			{
				sunrise=t.split(": ")[1];
				fields.add("sunrise");
				argvs.add("'"+sunrise+"'");
			}
			if(t.contains("Sunset"))
			{
				sunset=t.split(": ")[1];
				fields.add("sunset");
				argvs.add("'"+sunset+"'");
			}
			
			
		}
		
		
		}
		catch(Exception e){System.err.println(e); return -1;}
		
		//pubdate splits
		
		try {  
			
			DateFormat formatter ; 
			formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			java.util.Date d = (java.util.Date)formatter.parse(pubDate);  
			Timestamp pub_date=new Timestamp(d.getTime());
			
			if (i==0)
				initial_day=(Timestamp)pub_date.clone();
			
			Date date=new Date(initial_day.getYear(),initial_day.getMonth(),initial_day.getDate()+i);
			
			date_str=date.toString();
			pub_date_str=pub_date.toString();
			
			fields.add("pub_date");
			fields.add("Date_weather");
			argvs.add("'"+pub_date_str+"'");
			argvs.add("'"+date_str+"'");
			
			
		  } catch (Exception e)
		  {System.out.println("Exception :"+e);  return -1;}
		  
		  
		  /******************************************************************/
		  
		  //insert in db
		  
		   	//db instructions
	        
	        //open connection
	        
	        	db.openDatabase();
	        	//element with the same date
		        ArrayList<String> list=db.selectDatabase("*", "Data", "city="+city.getId()+" AND Date_weather='"+date_str+"'");
	        	db.closeDatabase();
	        	
	        	
	        	
	        	
	        	if (list.isEmpty()) //insert
	        	{
	        			        		
	        		//fields and argsv to array
	        		String[] f=new String[fields.size()];
	        		String[] a=new String[argvs.size()];
	        		for (int j=0;j<fields.size();j++)
	        		{
	        			f[j]=fields.get(j);
	        			a[j]=argvs.get(j);
	        		}
	        		
	        		db.openDatabase();
	        		db.insertDatabase("Data",f ,a);
	        		db.closeDatabase();
	        		
	        	}
	        	else //update
	        	{
	        		//fields and argsv to array
	        		String[] f=new String[fields.size()];
	        		String[] a=new String[argvs.size()];
	        		for (int j=0;j<fields.size();j++)
	        		{
	        			f[j]=fields.get(j);
	        			a[j]=argvs.get(j);
	        		}
	        		
	        		db.openDatabase();
	        		db.updateDatabase("Data", f, a, "city="+city.getId()+" AND Date_weather='"+date_str+"'");
	        		db.closeDatabase();
	        	}
	        
		
	        
		return 0;		
	}

}
