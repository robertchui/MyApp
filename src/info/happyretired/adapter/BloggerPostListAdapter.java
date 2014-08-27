package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.R;
import info.happyretired.ult.URLImageParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BloggerPostListAdapter extends ArrayAdapter<Blogger> {
	
	private Context context;
	private ArrayList<Blogger> activityItems;
	private View container;
	
	
	
	public BloggerPostListAdapter(Context ctx, int textViewResourceId, ArrayList<Blogger> activityItems){
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
		LayoutInflater mInflater = (LayoutInflater)
                  context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		  
		
		if(activityItems.get(position).getListType()!=null && activityItems.get(position).getListType().equals("Header")){
			convertView = mInflater.inflate(R.layout.listview_blog_post_header, null);
			TextView blog_name = (TextView) convertView.findViewById(R.id.blog_name);
			TextView blogger = (TextView) convertView.findViewById(R.id.blogger);
			TextView selfintro = (TextView) convertView.findViewById(R.id.selfintro);
			TextView updateStatus = (TextView) convertView.findViewById(R.id.updatestatus);
			ImageView cover = (ImageView) convertView.findViewById(R.id.cover);
			 
			blog_name.setText(activityItems.get(position).getBlog_name());
			blogger.setText(activityItems.get(position).getUser_name());
			selfintro.setText(activityItems.get(position).getSelf_intro());
			updateStatus.setText(activityItems.get(position).getUpdate_remark());
			
			if(activityItems.get(position).getAvatar_url()!=null && !activityItems.get(position).getAvatar_url().equals("null") && !activityItems.get(position).getAvatar_url().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getAvatar_url(), cover);
	       	else
	       		ImageLoader.getInstance().displayImage("", cover);
		}
		else{
			convertView = mInflater.inflate(R.layout.listitem_blog_post, null);
			TextView title = (TextView) convertView.findViewById(R.id.forum_topic);
			TextView lastPostTime = (TextView) convertView.findViewById(R.id.last_post_time);
			TextView category = (TextView) convertView.findViewById(R.id.category);
			ImageView coverImageView = (ImageView) convertView.findViewById(R.id.cover);
			
			title.setText(activityItems.get(position).getLast_post_title());
			lastPostTime.setText(activityItems.get(position).getLast_post_time());
			category.setText(activityItems.get(position).getCategory_name());
			
			if(activityItems.get(position).getCoverUrl()!=null && !activityItems.get(position).getCoverUrl().equals("null") && !activityItems.get(position).getCoverUrl().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+"/"+activityItems.get(position).getCoverUrl(), coverImageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", coverImageView);
			//view.setText(activityItems.get(position).getView());
		}

		
        return convertView;
	}
	
}
