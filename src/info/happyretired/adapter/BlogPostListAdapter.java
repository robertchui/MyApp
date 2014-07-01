package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.BlogPostItem;
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

public class BlogPostListAdapter extends ArrayAdapter<Blogger> {
	
	private Context context;
	private ArrayList<Blogger> activityItems;
	
	static class ForumViewHolder{
		TextView topic;
		TextView bloggerName;
		TextView lastPostTime;
		TextView replyAndView;
		ImageView coverImageView;
	}
	
	public BlogPostListAdapter(Context ctx, int textViewResourceId, ArrayList<Blogger> activityItems){
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
		
		ForumViewHolder viewHolder;
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listitem_blog_topics_with_cover, null);
            
            viewHolder = new ForumViewHolder();
            viewHolder.topic = (TextView) convertView.findViewById(R.id.forum_topic);
            viewHolder.bloggerName = (TextView) convertView.findViewById(R.id.last_post_name);
            viewHolder.lastPostTime = (TextView) convertView.findViewById(R.id.last_post_time);
            viewHolder.replyAndView = (TextView) convertView.findViewById(R.id.reply_and_view);
            //viewHolder.category = (TextView) convertView.findViewById(R.id.forum_category);
            viewHolder.coverImageView = (ImageView) convertView.findViewById(R.id.cover);
            convertView.setTag(viewHolder);
            
        }
		else{
			viewHolder = (ForumViewHolder)convertView.getTag();
		}
		 
        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView1);
		/*
		TextView topic = (TextView) convertView.findViewById(R.id.forum_topic);
		TextView lastUserName = (TextView) convertView.findViewById(R.id.last_post_name);
		TextView lastPostTime = (TextView) convertView.findViewById(R.id.last_post_time);
		TextView replyAndView = (TextView) convertView.findViewById(R.id.reply_and_view);
		TextView forumCategory = (TextView) convertView.findViewById(R.id.forum_category);
		*/

		
		viewHolder.topic.setText(activityItems.get(position).getLast_post_title());
		viewHolder.bloggerName.setText(activityItems.get(position).getUser_name());
		viewHolder.replyAndView.setText(activityItems.get(position).getView() );
		viewHolder.lastPostTime.setText(activityItems.get(position).getLast_post_time());
		//viewHolder.category.setText(activityItems.get(position).getCategory_name());
	
		if(activityItems.get(position).getCoverUrl()!=null && !activityItems.get(position).getCoverUrl().equals("null") && !activityItems.get(position).getCoverUrl().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getCoverUrl(), viewHolder.coverImageView);
       	else
       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
		
        
        return convertView;
	}

}
