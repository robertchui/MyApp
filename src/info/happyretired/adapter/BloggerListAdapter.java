package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.R;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class BloggerListAdapter extends ArrayAdapter<Blogger> {
	
	private Context context;
	private ArrayList<Blogger> activityItems;
	
	static class BlogViewHolder{
		TextView blogName;
		TextView bloggerName;
		ImageView coverImageView;
		TextView last_post_title;
		TextView last_post;
	}
	
	public BloggerListAdapter(Context ctx, int textViewResourceId, ArrayList<Blogger> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public Blogger getItem(int position) {		
		return activityItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		BlogViewHolder viewHolder;
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listitem_blogger, null);
            
            viewHolder = new BlogViewHolder();
            viewHolder.blogName = (TextView) convertView.findViewById(R.id.blog_name);
            viewHolder.bloggerName = (TextView) convertView.findViewById(R.id.blogger_name);
            viewHolder.coverImageView = (ImageView) convertView.findViewById(R.id.cover);
            viewHolder.last_post_title = (TextView) convertView.findViewById(R.id.last_post_title);
            
            convertView.setTag(viewHolder);
            
        }
		else{
			viewHolder = (BlogViewHolder)convertView.getTag();
		}

		if(activityItems.get(position).getBlog_name()!=null && !activityItems.get(position).getBlog_name().equals("null"))
			viewHolder.blogName.setText(activityItems.get(position).getBlog_name());
		
		viewHolder.bloggerName.setText(activityItems.get(position).getUser_name());
		
		
		if(activityItems.get(position).getAvatar_url()!=null && !activityItems.get(position).getAvatar_url().equals("null") && !activityItems.get(position).getAvatar_url().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getAvatar_url(), viewHolder.coverImageView);
       	else
       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
		
		viewHolder.last_post_title.setText(activityItems.get(position).getLast_post_title());
		
		//viewHolder.bloggerName.setText(activityItems.get(position).getUser_name());
        
		
        return convertView;
	}

}
