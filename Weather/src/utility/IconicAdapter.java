package utility;


import src.weather.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconicAdapter extends ArrayAdapter<String>{
	Activity context;
	String[] items;
	Integer[] images;
	public IconicAdapter(Activity context,String[] i,Integer[] images)
	{
		super(context,R.layout.row_options_city_weather,i);
		this.context=context;
		this.items=i;
		this.images=images;
	}
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		LayoutInflater inflater=context.getLayoutInflater();
		View row=inflater.inflate(R.layout.row_options_city_weather, null);
		TextView option=(TextView) row.findViewById(R.id.option);
		ImageView icon=(ImageView) row.findViewById(R.id.icon);
		option.setText(items[position]);
		
		icon.setImageResource(images[position]);
		
		return row;
	}

}
