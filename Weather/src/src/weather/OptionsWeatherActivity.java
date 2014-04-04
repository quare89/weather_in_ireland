package src.weather;

import utility.IconicAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import dataManager.City;
import dataManager.DbConnection;
import dataManager.GrabThread;


public class OptionsWeatherActivity extends Activity implements OnItemClickListener {
	
	//the considered city
	City city;
	
	/*Menu functionalities*/
	ListView listOperation;
	String[] items={"Display Weather","Grab Data","View History"};
	Integer[] icons={R.drawable.weather,R.drawable.web,R.drawable.history};
	
	/*intents for start the new activities*/
	Intent history_intent;
	Intent display_intent;
	Bundle myBundle;
	
	/*controls in the layout*/
	TextView tv_city;
	TextView tv_country;	
	TextView tv_long;
	TextView tv_lat;
	TextView tv_alt;
	
	
	/*
	//Thread for grab data
	Handler handler=new Handler(){
		
		public void handleMessage(Message msg){
			
			
			
			String returnedValue=(String)msg.obj;
			//do something with the value sent by the background thread here
			
			pd.show();		
			
		}
		
	};
	*/
	
	private ProgressDialog pd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
       setContentView(R.layout.activity_options_weather);
       Intent myLocalIntent=getIntent();
       
       //extract the city
       myLocalIntent=getIntent();
       myBundle=myLocalIntent.getExtras();
       city=(City)myBundle.getSerializable("city");
             
       
       //find textviews
       tv_city=(TextView) findViewById(R.id.tv_city);
       tv_country=(TextView) findViewById(R.id.tv_country);
       tv_alt=(TextView) findViewById(R.id.tv_alt);
       tv_long=(TextView) findViewById(R.id.tv_long);
       tv_lat=(TextView) findViewById(R.id.tv_lat);
       
       //update the textviews
       tv_city.setText(city.getName());
       tv_country.setText("("+city.getCountry()+")");
       tv_long.setText(city.printCoords(city.getLongitude())+" W");
       tv_lat.setText(city.printCoords(city.getLatitude())+" N");
       tv_alt.setText(String.valueOf(city.getAltitude())+"mt.");
       
       //new intents
       display_intent= new Intent(OptionsWeatherActivity.this,DisplayWeatherActivity.class);
       history_intent= new Intent(OptionsWeatherActivity.this,HistoryActivity.class);
       
       //list menu construction
       listOperation=(ListView) findViewById(R.id.listOperation);
       listOperation.setAdapter(new IconicAdapter(this,items,icons));
       
       listOperation.setOnItemClickListener(this);
       

       ImageButton back=(ImageButton) findViewById(R.id.b_back);
       back.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
             
           	finish();
           }
     });
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_options_weather, menu);
        
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		if(position==0)
		{
			display_intent.putExtras(myBundle);
			
			startActivity(display_intent);
		}
		else if(position==1)
		{
			pd=ProgressDialog.show(OptionsWeatherActivity.this,"Grabbing the data","Loading...");
			
			String path=Environment.getExternalStorageDirectory().getPath()+"/dbWeather.sqlite";
	        DbConnection db=new DbConnection(this,path);
	       					
			GrabThread background=new GrabThread(pd,city,db);
			
			background.start();
		}
		else if(position==2)
		{
			
			history_intent.putExtras(myBundle);
			
			startActivity(history_intent);
		}
			
		
		
	}
}
