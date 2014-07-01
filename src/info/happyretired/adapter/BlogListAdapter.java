package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BlogListAdapter extends ArrayAdapter<ActivityItem> {
	
	private Context context;
	private ArrayList<ActivityItem> activityItems;
	
	public BlogListAdapter(Context ctx, int textViewResourceId, ArrayList<ActivityItem> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public ActivityItem getItem(int position) {		
		return activityItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listitem, null);
        }
		 
        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView1);
		TextView textMonth = (TextView) convertView.findViewById(R.id.textMonth);
		TextView textDate = (TextView) convertView.findViewById(R.id.textDate);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView textTime = (TextView) convertView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        /*
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        imageView.setLayoutParams(params);
        */
        //
        Date convertedDate = null;
        try{
	        DateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
	        convertedDate = (Date) formatter.parse(activityItems.get(position).getDateFrom());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        textMonth.setText(String.valueOf(convertedDate.getMonth()+1)+"ды");
        textDate.setText(activityItems.get(position).getDateFrom().substring(6, 8));
        txtTitle.setText(activityItems.get(position).getTitle());
        
        String tmp = "";
        if(activityItems.get(position).getTimeTo().length()>0)
        	tmp = " - ";
       	textTime.setText(activityItems.get(position).getTimeFrom()+tmp+ activityItems.get(position).getTimeTo());

       	//String url = "https://www.happy-retired.com/images/photoupload/files/20140503185627/thumbnail/sick-bear.jpg";
       	
       	
       	//Log.i(ActivityListAdapter.class.getName(), R.string.web_url+activityItems.get(position).getImageURL());
       	Log.i(BlogListAdapter.class.getName(), activityItems.get(position).getRefNo()+activityItems.get(position).getImageURL());
       	
       	if(activityItems.get(position).getImageURL()!=null && !activityItems.get(position).getImageURL().equals("null") && !activityItems.get(position).getImageURL().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+activityItems.get(position).getImageURL(), imageView);
       	else
       		ImageLoader.getInstance().displayImage("", imageView);
        
        return convertView;
	}

}
