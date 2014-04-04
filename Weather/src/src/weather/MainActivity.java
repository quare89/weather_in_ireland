package src.weather;

import java.util.ArrayList;

import dataManager.City;
import dataManager.DbConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	//define here all the buttons
	Button buttCork,buttLimerick,buttDublin,buttSligo;
	Button buttDerry,buttGalway,buttWaterford,buttDrogheda;
	Button buttBelfast,buttDingle;
	City city;
	
	Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        intent= new Intent(MainActivity.this,OptionsWeatherActivity.class);
        
        //initialise here all the buttons
        buttCork=(Button) findViewById(R.id.b_cork); 
        buttLimerick=(Button) findViewById(R.id.b_limerick); 
        buttDublin=(Button) findViewById(R.id.b_dublin); 
        buttSligo=(Button) findViewById(R.id.b_sligo); 
        buttDerry=(Button) findViewById(R.id.b_derry); 
        buttGalway=(Button) findViewById(R.id.b_galway); 
        buttWaterford=(Button) findViewById(R.id.b_waterford); 
        buttDrogheda=(Button) findViewById(R.id.b_drogheda); 
        buttBelfast=(Button) findViewById(R.id.b_belfast); 
        buttDingle=(Button) findViewById(R.id.b_dingle); 
        
    
        //click Listener of all the buttons
        buttCork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(1);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttLimerick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(2);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttDublin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(3);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttSligo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(4);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttDerry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(5);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttGalway.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(6);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttWaterford.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(7);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttDrogheda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(8);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttBelfast.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(9);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        
        buttDingle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				initializeCity(10);
				myBundle.putSerializable("city", city); //id number of city in db
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	
    
    private void initializeCity(int id)
    {
    	//put data in the layout
        String path=Environment.getExternalStorageDirectory().getPath()+"/dbWeather.sqlite";
        
        DbConnection db=new DbConnection(this,path);
        
        //db instructions
        
        //open connection
        db.openDatabase();
        //There is only one element in list, the element with the id=city_id. 
        ArrayList<String> list=db.selectDatabase("*", "City", "id="+id);
        //close connection
        db.closeDatabase();
        
        //the features in list are separated by ";;"
        String featuresStr=list.get(0);
        String[] features=featuresStr.split(";;");
        
        //id;;name;;country;;longitude;;latitude;;altitude
        int id_city=Integer.parseInt(features[0]);
        String name=features[1];
        String country=features[2];
        double longit=Double.parseDouble(features[3]);
        double latit=Double.parseDouble(features[4]);
        int altit=Integer.parseInt(features[5]);
        int cod_rss=Integer.parseInt(features[6]);
        
        //create object City
        city=new City(id_city,name,country,longit,latit,altit,cod_rss);
   }
    
    
}
