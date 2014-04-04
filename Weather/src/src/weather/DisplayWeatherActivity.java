package src.weather;

import java.util.List;
import java.util.Vector;

import utility.PagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import dataManager.City;
import dataManager.DataWeather;
import dataManager.DataWeatherExtractor;
import dataManager.DbConnection;
import display.PageFragment;

public class DisplayWeatherActivity extends FragmentActivity implements OnClickListener,OnPageChangeListener 
{

	//the considered city
	City city;
	
	Bundle myBundle;
	
	// list contains fragments to instantiate in the viewpager
        List<Fragment> fragments = new Vector<Fragment>();
        // page adapter between fragment list and view pager
        private PagerAdapter mPagerAdapter;
        // view pager
        private ViewPager mPager;
        
    Button button1,button2,button3;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);
        
        Intent myLocalIntent=getIntent();
        
        //extract the city
        myLocalIntent=getIntent();
        myBundle=myLocalIntent.getExtras();
        city=(City)myBundle.getSerializable("city");
        
        String path=Environment.getExternalStorageDirectory().getPath()+"/dbWeather.sqlite";
        DbConnection db=new DbConnection(this,path);
        
        DataWeatherExtractor dw_extr=new DataWeatherExtractor(db,city);
        String whereClause="city="+city.getId()+"  ORDER BY Date_weather DESC LIMIT 3";
        dw_extr.extractFromDb(whereClause);
        DataWeather[] dws=dw_extr.getDw();
             
        TextView tv_city=(TextView) findViewById(R.id.tv_city);
        TextView tv_country=(TextView) findViewById(R.id.tv_country);
        
        tv_city.setText(city.getName());
        tv_country.setText("("+city.getCountry()+")");

        // creating fragments and adding to list
        Fragment f1=Fragment.instantiate(this,PageFragment.class.getName());
        Fragment f2=Fragment.instantiate(this,PageFragment.class.getName());
        Fragment f3=Fragment.instantiate(this,PageFragment.class.getName());
        	
        //put arguments in fragments
        Bundle fragBundle1=new Bundle();
        Bundle fragBundle2=new Bundle();
        Bundle fragBundle3=new Bundle();
		fragBundle1.putSerializable("data", dws[2]); 
		f1.setArguments(fragBundle1);
        
		fragBundle2.putSerializable("data", dws[1]);
		f2.setArguments(fragBundle2);
       
        fragBundle3.putSerializable("data", dws[0]); 
        f3.setArguments(fragBundle3);
        
        
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        
        
        // upper bar button listener, allows direct page access
        button1 = (Button)findViewById(R.id.btnPag1);
        button2 = (Button)findViewById(R.id.btnPag2);
        button3 = (Button)findViewById(R.id.btnPag3);  
        
        //buttons names
        String[] weekDays={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        
        button1.setText(weekDays[dws[2].getDate_weather().getDay()]+" "+dws[2].getDate_weather().getDate());
        button2.setText(weekDays[dws[1].getDate_weather().getDay()]+" "+dws[1].getDate_weather().getDate());
        button3.setText(weekDays[dws[0].getDate_weather().getDay()]+" "+dws[0].getDate_weather().getDate());
        
        // creating adapter and linking to view pager
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(),fragments);
        mPager = (ViewPager) super.findViewById(R.id.pager);
        mPager.setAdapter(this.mPagerAdapter);
           
       mPager.setOnPageChangeListener(this);
           
        
        
        button1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		mPager.setCurrentItem(0);   // go to first page
        		button1.setBackgroundResource(R.drawable.button_on);
        		button2.setBackgroundResource(R.drawable.button_off);
        		button3.setBackgroundResource(R.drawable.button_off);
        		
        	}
        });
        
        button2.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
                mPager.setCurrentItem(1);   // go to second page
                button1.setBackgroundResource(R.drawable.button_off);
         		button2.setBackgroundResource(R.drawable.button_on);
         		button3.setBackgroundResource(R.drawable.button_off);
         		
              }
        });
        
        button3.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
                mPager.setCurrentItem(2);   // go to third page
                button1.setBackgroundResource(R.drawable.button_off);
         		button2.setBackgroundResource(R.drawable.button_off);
         		button3.setBackgroundResource(R.drawable.button_on);
         		
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
        getMenuInflater().inflate(R.menu.activity_display_weather, menu);
        return true;
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		
			    
	      Button b1=(Button) findViewById(R.id.btnPag1);
	      Button b2=(Button) findViewById(R.id.btnPag2);
	      Button b3=(Button) findViewById(R.id.btnPag3);
	      
	       if(position==0)
		   {
			   b1.setBackgroundResource(R.drawable.button_on);
			   b2.setBackgroundResource(R.drawable.button_off);
			   b3.setBackgroundResource(R.drawable.button_off);
		   }   
		   else if(position==1)
		   {
			   b1.setBackgroundResource(R.drawable.button_off);
			   b2.setBackgroundResource(R.drawable.button_on);
			   b3.setBackgroundResource(R.drawable.button_off);
		   }   
		   else
		   {
			   b1.setBackgroundResource(R.drawable.button_off);
			   b2.setBackgroundResource(R.drawable.button_off);
			   b3.setBackgroundResource(R.drawable.button_on);
		   }   
	}
}
