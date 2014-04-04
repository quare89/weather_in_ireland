package src.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

	
	private static final int STOPSPLASH = 0;
	   //time in milliseconds
	   private static final long SPLASHTIME = 5000;
	   
	   private RelativeLayout splash;
	   Intent intent;
	 //handler for splash screen
	   private Handler splashHandler = new Handler() {
	      
	      @Override
	      public void handleMessage(Message msg) {
	         switch (msg.what) {
	         case STOPSPLASH:
	            //remove SplashScreen from view
	            
	            intent= new Intent(SplashActivity.this,MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	            startActivity(intent);
	            overridePendingTransition(0, 0);
	            splash = (RelativeLayout) findViewById(R.id.splashscreen);
	    		Animation fo = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadeout);
	    		splash.startAnimation(fo);
	            finish();
	            break;
	         }
	         super.handleMessage(msg);
	      }
	   };
	
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.activity_splash);
		
		   ImageView tv = (ImageView) findViewById(R.id.iv);
		   Animation rot = AnimationUtils.loadAnimation(this, R.anim.fadein);
		   tv.startAnimation(rot);
		
		   Message msg = new Message();
		   msg.what = STOPSPLASH;
		   splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        
        
	   }	

}
