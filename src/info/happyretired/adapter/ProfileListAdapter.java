package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.ForumCategoryItem;
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

public class ProfileListAdapter extends ArrayAdapter<ForumCategoryItem> {
	
	private Context context;
	private ArrayList<ForumCategoryItem> activityItems;
	
	public ProfileListAdapter(Context ctx, int textViewResourceId, ArrayList<ForumCategoryItem> activityItems){
		super(ctx, R.layout.listitem_profile, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public ForumCategoryItem getItem(int position) {		
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
            convertView = mInflater.inflate(R.layout.listitem_profile, null);
        }
		 
        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView1);
		TextView categoryName = (TextView) convertView.findViewById(R.id.forum_category_name);
		categoryName.setText(activityItems.get(position).getName());
        
        return convertView;
	}

}
