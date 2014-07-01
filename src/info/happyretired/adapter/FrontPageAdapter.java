package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.JobItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.model.VolunteerItem;
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

public class FrontPageAdapter extends ArrayAdapter {
	
	private Context context;
	private ArrayList activityItems;
	
	static class ViewHolder{
		TextView blogName;
		TextView bloggerName;
		ImageView coverImageView;
		TextView last_post_title;
		TextView last_post;
	}
	
	public FrontPageAdapter(Context ctx, int textViewResourceId, ArrayList<Blogger> activityItems){
		super(ctx, R.layout.listitem, textViewResourceId);
		this.context = ctx;
		this.activityItems = activityItems;
	}

	@Override
	public int getCount() {
		return activityItems.size();
	}

	@Override
	public Object getItem(int position) {		
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
            convertView = mInflater.inflate(R.layout.listitem_frontpage, null);
            
            viewHolder = new ViewHolder();
            viewHolder.blogName = (TextView) convertView.findViewById(R.id.blog_name);
            viewHolder.bloggerName = (TextView) convertView.findViewById(R.id.blogger_name);
            viewHolder.coverImageView = (ImageView) convertView.findViewById(R.id.cover);
            viewHolder.last_post_title = (TextView) convertView.findViewById(R.id.last_post_title);
            
            convertView.setTag(viewHolder);
            
        }
		else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		if(activityItems.get(position) instanceof ActivityItem){
			ActivityItem item = (ActivityItem)activityItems.get(position);
			
			if(item.getImageURL()!=null && !item.getImageURL().equals("null") && !item.getImageURL().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+item.getImageURL(), viewHolder.coverImageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
			
			viewHolder.blogName.setText(item.getTitle());
			
			viewHolder.last_post_title.setText(item.getFee());
			
			 Date convertedDate = null;
	         try{
	        	 DateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
	    	     convertedDate = (Date) formatter.parse(item.getDateFrom());
	         }
	         catch(Exception e){
	        	 e.printStackTrace();
            }			
	         viewHolder.bloggerName.setText((convertedDate.getMonth()+1)+"ค๋"+convertedDate.getDate()+"ค้");
		}
		if(activityItems.get(position) instanceof ForumTopicItem){
			ForumTopicItem item = (ForumTopicItem)activityItems.get(position);
			
			if(item.getCoverImgUrl()!=null && !item.getCoverImgUrl().equals("null") && !item.getCoverImgUrl().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+item.getCoverImgUrl(), viewHolder.coverImageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
			
			viewHolder.blogName.setText(item.getSubject());
			viewHolder.bloggerName.setText(item.getCategory_name());
			viewHolder.last_post_title.setText(item.getHits());
			viewHolder.last_post_title.setVisibility(View.GONE);
	        
		}
		if(activityItems.get(position) instanceof Blogger){
			Blogger item = (Blogger)activityItems.get(position);
			
			if(item.getCoverUrl()!=null && !item.getCoverUrl().equals("null") && !item.getCoverUrl().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+item.getCoverUrl(), viewHolder.coverImageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
			
			viewHolder.blogName.setText(item.getLast_post_title());
			viewHolder.bloggerName.setText(item.getCategory_name());
			viewHolder.last_post_title.setText(item.getUser_name());
			//viewHolder.last_post_title.setVisibility(View.GONE);
	        
		}
		if(activityItems.get(position) instanceof VolunteerItem){
			VolunteerItem item = (VolunteerItem)activityItems.get(position);
			
			if(item.getImageURL()!=null && !item.getImageURL().equals("null") && !item.getImageURL().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+item.getImageURL(), viewHolder.coverImageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
			
			viewHolder.blogName.setText(item.getTitle());
			viewHolder.bloggerName.setText(item.getCompanyName());
			viewHolder.last_post_title.setText(item.getTargetgroupDesc());
			//viewHolder.last_post_title.setVisibility(View.GONE);
	        
		}
		if(activityItems.get(position) instanceof JobItem){
			JobItem item = (JobItem)activityItems.get(position);
			
			if(item.getCompanyImgUrl()!=null && !item.getCompanyImgUrl().equals("null") && !item.getCompanyImgUrl().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+item.getCompanyImgUrl(), viewHolder.coverImageView);
	       	else	       		
	       		viewHolder.coverImageView.setVisibility(View.GONE);
			viewHolder.blogName.setLines(3);
			viewHolder.blogName.setText(item.getTitle());
			viewHolder.bloggerName.setSingleLine(false);
			viewHolder.bloggerName.setText(item.getCompanyName());
			//viewHolder.last_post_title.setText(item.getTargetgroupDesc());
			//viewHolder.last_post_title.setVisibility(View.GONE);
	        
		}
		/*
		if(activityItems.get(position).getBlog_name()!=null && !activityItems.get(position).getBlog_name().equals("null"))
			viewHolder.blogName.setText(activityItems.get(position).getBlog_name());
		
		viewHolder.bloggerName.setText(activityItems.get(position).getUser_name());
		
		
		if(activityItems.get(position).getAvatar_url()!=null && !activityItems.get(position).getAvatar_url().equals("null") && !activityItems.get(position).getAvatar_url().isEmpty())
       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getAvatar_url(), viewHolder.coverImageView);
       	else
       		ImageLoader.getInstance().displayImage("", viewHolder.coverImageView);
		
		viewHolder.last_post_title.setText(activityItems.get(position).getLast_post_title());
		
		//viewHolder.bloggerName.setText(activityItems.get(position).getUser_name());
        */
		
        return convertView;
	}

}
