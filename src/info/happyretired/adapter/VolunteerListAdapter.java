package info.happyretired.adapter;

import info.happyretired.adapter.EventListAdapter.ViewHolder;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.model.VolunteerItem;
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

public class VolunteerListAdapter extends ArrayAdapter<VolunteerItem> {
	
	private Context context;
	private ArrayList<VolunteerItem> activityItems;
	
	static class ViewHolder{
		TextView textMonth;
		TextView textDate;
		TextView txtTitle;
		TextView company;
		TextView textTime;
		ImageView imageView;

	}
	
	public VolunteerListAdapter(Context ctx, int textViewResourceId, ArrayList<VolunteerItem> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public VolunteerItem getItem(int position) {		
		return activityItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listitem_volunteer, null);
            
            viewHolder = new ViewHolder();
            viewHolder.textMonth = (TextView) convertView.findViewById(R.id.textMonth);
            viewHolder.textDate = (TextView) convertView.findViewById(R.id.textDate);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.company = (TextView) convertView.findViewById(R.id.textCompany);
            viewHolder.textTime = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(viewHolder);
        }
		else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		 
        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView1);
		
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
        
        viewHolder.textMonth.setText(String.valueOf(convertedDate.getMonth()+1)+"ды");
        viewHolder.textDate.setText(activityItems.get(position).getDateFrom().substring(6, 8));
        viewHolder.txtTitle.setText(activityItems.get(position).getTitle());
        viewHolder.company.setText(activityItems.get(position).getCompanyName());
        String tmp = "";
        if(activityItems.get(position).getTimeTo().length()>0)
        	tmp = " - ";
        viewHolder.textTime.setText(activityItems.get(position).getTimeFrom()+tmp+ activityItems.get(position).getTimeTo());

       	//String url = "https://www.happy-retired.com/images/photoupload/files/20140503185627/thumbnail/sick-bear.jpg";
       	
       	
       	//Log.i(ActivityListAdapter.class.getName(), R.string.web_url+activityItems.get(position).getImageURL());
       	Log.i(VolunteerListAdapter.class.getName(), activityItems.get(position).getRefNo()+activityItems.get(position).getImageURL());
       	
       	if(activityItems.get(position).getImageURL()!=null && !activityItems.get(position).getImageURL().equals("null") && !activityItems.get(position).getImageURL().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+activityItems.get(position).getImageURL(), viewHolder.imageView);
       	else{
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/images/nophoto300.jpg", viewHolder.imageView);
       		//imageView.setVisibility(View.GONE);
       	}
        
        return convertView;
	}

}
