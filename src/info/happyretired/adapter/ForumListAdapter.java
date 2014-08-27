package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.ult.URLImageParser;
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
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForumListAdapter extends ArrayAdapter<ForumTopicItem> {
	
	private Context context;
	private ArrayList<ForumTopicItem> activityItems;
	
	static class ForumViewHolder{
		ImageView advertistment;
		TextView topic;
		TextView lastUserName;
		RelativeLayout rl;
		//TextView lastPostTime;
		//TextView replyAndView;
		TextView forumCategory;
		ImageView coverImageView;
	}
	
	public ForumListAdapter(Context ctx, int textViewResourceId, ArrayList<ForumTopicItem> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public ForumTopicItem getItem(int position) {		
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
            convertView = mInflater.inflate(R.layout.listitem_forum_topics_with_cover, null);
            
            viewHolder = new ForumViewHolder();
            
            viewHolder.rl = (RelativeLayout)convertView.findViewById(R.id.topic_details);
            viewHolder.advertistment = (ImageView) convertView.findViewById(R.id.advertistment);
            viewHolder.topic = (TextView) convertView.findViewById(R.id.forum_topic);
            viewHolder.lastUserName = (TextView) convertView.findViewById(R.id.last_post_name);
            //viewHolder.lastPostTime = (TextView) convertView.findViewById(R.id.last_post_time);
            //viewHolder.replyAndView = (TextView) convertView.findViewById(R.id.reply_and_view);
            viewHolder.forumCategory = (TextView) convertView.findViewById(R.id.forum_category);
            viewHolder.coverImageView = (ImageView) convertView.findViewById(R.id.cover);
            convertView.setTag(viewHolder);
            
        }
		else{
			viewHolder = (ForumViewHolder)convertView.getTag();
		}
		
		if(activityItems.get(position).getAdvertisementImgUrl()!=null && !activityItems.get(position).getAdvertisementImgUrl().equals("null") && !activityItems.get(position).getAdvertisementImgUrl().isEmpty()){
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getAdvertisementImgUrl(), viewHolder.advertistment);
       		
       		/*
       		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
       		lp.setMargins(0, 0, 0, 10);
       		viewHolder.advertistment.setLayoutParams(lp);
       		*/
       		
       		
       		
       		viewHolder.advertistment.setVisibility(View.VISIBLE);
       		viewHolder.rl.setVisibility(View.GONE);
		}
       	else{
       		ImageLoader.getInstance().displayImage("", viewHolder.advertistment);
       		viewHolder.advertistment.setVisibility(View.GONE);
       		viewHolder.rl.setVisibility(View.VISIBLE);
       		
			viewHolder.topic.setText(activityItems.get(position).getSubject());
			viewHolder.lastUserName.setText(activityItems.get(position).getLast_post_guest_name());
			//viewHolder.replyAndView.setText(activityItems.get(position).getPosts() + "/" + activityItems.get(position).getHits() );
			//viewHolder.lastPostTime.setText(activityItems.get(position).getLast_post_time());
			viewHolder.forumCategory.setText(activityItems.get(position).getCategory_name());
		
			if(activityItems.get(position).getCoverImgUrl()!=null && !activityItems.get(position).getCoverImgUrl().equals("null") && !activityItems.get(position).getCoverImgUrl().isEmpty()){
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getCoverImgUrl(), viewHolder.coverImageView);
	       		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	       		params.setMargins(0, 0, 200, 0);
	       		//viewHolder.rl.setLayoutParams(params);
	       		viewHolder.topic.setPadding(0, 0, 200, 0);
			}
	       	else{
	       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
	       		
	       		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	       		params.setMargins(0, 0, 0, 0);
	       		//viewHolder.rl.setLayoutParams(params);
	       	}
       	}
		
        
        return convertView;
	}

}
