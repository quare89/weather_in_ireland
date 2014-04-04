package utility;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

	   // fragments to instantiate in the viewpager
	   private List<Fragment> fragments;
	   
	   
	   
	   // constructor
	   public PagerAdapter(FragmentManager fm,List<Fragment> fragments) {
	      super(fm);
	      this.fragments = fragments;
	   }
	   
	   // return access to fragment from position, required override
	   @Override
	   public Fragment getItem(int position) {
		  
	      return this.fragments.get(position);
	   }

	   public int getItemPosition(Fragment frag){
		   return this.fragments.indexOf(frag);
	   }
	   
	   // number of fragments in list, required override
	   @Override
	   public int getCount() {
	      return this.fragments.size();
	   }
}
