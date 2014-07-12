package info.happyretired.adapter;

import info.happyretired.adapter.ForumListAdapter.ForumViewHolder;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.JetsoItem;
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

public class JetsoListAdapter extends ArrayAdapter<JetsoItem> {
	
	private Context context;
	private ArrayList<JetsoItem> activityItems;
	
	static class ViewHolder{
		TextView brandName;
		TextView txtTitle;
		TextView textTime;
		ImageView imageView;

	}
	
	public JetsoListAdapter(Context ctx, int textViewResourceId, ArrayList<JetsoItem> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public JetsoItem getItem(int position) {		
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
            convertView = mInflater.inflate(R.layout.listitem_jetso, null);
            
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textTime = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.brandName = (TextView) convertView.findViewById(R.id.brand_name);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(viewHolder);
        }
		else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

        viewHolder.txtTitle.setText(activityItems.get(position).getTitle());
        viewHolder.brandName.setText(activityItems.get(position).getCompany_name());
        
       	if(activityItems.get(position).getImageURL()!=null && !activityItems.get(position).getImageURL().equals("null") && !activityItems.get(position).getImageURL().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+activityItems.get(position).getImageURL(), viewHolder.imageView);
       	else
       		ImageLoader.getInstance().displayImage("", viewHolder.imageView);
        
        return convertView;
	}

}
