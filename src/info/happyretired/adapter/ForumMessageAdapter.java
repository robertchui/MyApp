package info.happyretired.adapter;

import info.happyretired.model.ActivityItem;
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

public class ForumMessageAdapter extends ArrayAdapter<ForumTopicItem> implements ImageGetter {
	
	private Context context;
	private ArrayList<ForumTopicItem> activityItems;
	private View container;
	
	
	
	public ForumMessageAdapter(Context ctx, int textViewResourceId, ArrayList<ForumTopicItem> activityItems){
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
		LayoutInflater mInflater = (LayoutInflater)
                  context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		  
		
		if(activityItems.get(position).getListType()!=null && activityItems.get(position).getListType().equals("Header")){
			convertView = mInflater.inflate(R.layout.listview_header, null);
			TextView category = (TextView) convertView.findViewById(R.id.category);
			TextView subject = (TextView) convertView.findViewById(R.id.subject);
			TextView view = (TextView) convertView.findViewById(R.id.view);
			
			category.setText(activityItems.get(position).getCategory_name());
			subject.setText(activityItems.get(position).getSubject());
			view.setText(activityItems.get(position).getHits());
		}
		else{
			convertView = mInflater.inflate(R.layout.listitem_forum_messages, null);
			TextView lastUserName = (TextView) convertView.findViewById(R.id.last_post_name);
			TextView lastPostTime = (TextView) convertView.findViewById(R.id.last_post_time);
			TextView messageView = (TextView) convertView.findViewById(R.id.message);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);

			lastUserName.setText(activityItems.get(position).getLast_post_guest_name());
			lastPostTime.setText(activityItems.get(position).getLast_post_time());
			container = messageView;
			
			//URLImageParser p = new URLImageParser(messageView, context);
			Spanned sp = Html.fromHtml( activityItems.get(position).getMessage(), new URLImageParser(this, messageView, context) ,null);
			
			//messageView.loadDataWithBaseURL("", activityItems.get(position).getMessage(), "text/html", "UTF-8", null);
			
			messageView.setText(sp);
			if(activityItems.get(position).getLast_avatar_url()!=null && !activityItems.get(position).getLast_avatar_url().equals("null") && !activityItems.get(position).getLast_avatar_url().isEmpty())
	       		ImageLoader.getInstance().displayImage(context.getResources().getString(R.string.web_url)+activityItems.get(position).getLast_avatar_url(), imageView);
	       	else
	       		ImageLoader.getInstance().displayImage("", imageView);
		}
		

		
	
        
        return convertView;
	}
	

	@Override
    public Drawable getDrawable(String source) {
		
		
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = context.getResources().getDrawable(R.drawable.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);
        
        

        return d;
        
		/*
		
    	try {
    		Drawable drawable = Drawable.createFromStream(new URL(source).openStream(), "src name");
    		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
    		return drawable;
    		} catch(IOException exception) {
    		Log.v("IOException",exception.getMessage());
    		return null;
    		}
    		*/
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
           
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);

                TextView messageView = (TextView) container;
                messageView.setText(messageView.getText());
                
                //messageView.invalidate();
            }
        }
    }

}
