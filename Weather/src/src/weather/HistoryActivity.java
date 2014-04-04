package src.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import dataManager.City;

public class HistoryActivity extends Activity {

	//the considered city
	City city;
	
	Bundle myBundle;
	
	Button btn_temp_tot,btn_temp_month,btn_temp_year,btn_press,btn_humid;
	
	Intent intent;
	TextView tv_city;
	TextView tv_country;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        Intent myLocalIntent=getIntent();
        
        intent= new Intent(HistoryActivity.this,GraphActivity.class);
        
        //extract the city
        myLocalIntent=getIntent();
        myBundle=myLocalIntent.getExtras();
        city=(City)myBundle.getSerializable("city");
        
        
        btn_temp_tot=(Button) findViewById(R.id.btn_temp_tot);
        btn_temp_month=(Button) findViewById(R.id.btn_temp_month);
        btn_temp_year=(Button) findViewById(R.id.btn_temp_year);
        btn_press=(Button) findViewById(R.id.btn_pressure);
        btn_humid=(Button) findViewById(R.id.btn_humid);
        
      //find textviews
        tv_city=(TextView) findViewById(R.id.tv_city);
        tv_country=(TextView) findViewById(R.id.tv_country);
        
        tv_city.setText(city.getName());
        tv_country.setText("("+city.getCountry()+")");

      //click Listener of all the buttons
        btn_temp_tot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Bundle myBundle=new Bundle();
				myBundle.putSerializable("city", city); //id number of city in db
				myBundle.putInt("type", 1);
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        btn_temp_year.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				myBundle.putSerializable("city", city); //id number of city in db
				myBundle.putInt("type", 2);
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        btn_temp_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				myBundle.putSerializable("city", city); //id number of city in db
				myBundle.putInt("type", 3);
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        btn_humid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myBundle=new Bundle();
				myBundle.putSerializable("city", city); //id number of city in db
				myBundle.putInt("type", 4);
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
        btn_press.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
							
				Bundle myBundle=new Bundle();
				myBundle.putSerializable("city", city); //id number of city in db
				myBundle.putInt("type", 5);
				intent.putExtras(myBundle);
				
				startActivity(intent);
			}
		});
         


        ImageButton back=(ImageButton) findViewById(R.id.b_back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              
            	finish();
            }
      });

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history, menu);
        return true;
    }

}
