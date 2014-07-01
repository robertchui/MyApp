package info.happyretired.activity.blog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;















import info.happyretired.adapter.EventListAdapter;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class BlogDetailsFragment extends Fragment {

	private View mRoot;
	private Blogger activityItem;
	ProgressDialog mdialog; 
	private JSONArray jsonArray = new JSONArray();
	String refNo;
	String title;
	String webserviceURL;
	LinearLayout linlaHeaderProgress;
	
	TextView titleText;
	TextView post_date;
	ImageView imageView;
	TextView content;
	TextView blogger_name;
	
	
	private int page;
	private int totalPage;
	GetActivityItemTask task ;
	
	void setPageDetails(int page, int totalPage) {
		this.page = page;
		this.totalPage = totalPage;
	}
	
	public BlogDetailsFragment() {
		// Required empty public constructor
	}


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}



	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		
		try {
			mRoot = inflater.inflate(R.layout.fragment_blog_details, container,
			        false);

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		titleText = (TextView)mRoot.findViewById(R.id.title);
		post_date = (TextView)mRoot.findViewById(R.id.post_date);
		blogger_name = (TextView)mRoot.findViewById(R.id.blogger_name);
        imageView = (ImageView) mRoot.findViewById(R.id.cover); 
        content = (TextView)mRoot.findViewById(R.id.content);
		
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress2);
		if(jsonArray.length()==0){
			task = new GetActivityItemTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		updateLayout();
	

		
        return mRoot;
    }
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task!=null)
			task.cancel(true);

		linlaHeaderProgress.setVisibility(View.GONE);
	}

	
	
	public Blogger getActivityItem() {
		return activityItem;
	}

	public void setActivityItem(Blogger activityItem) {
		this.activityItem = activityItem;
	}

	public String readActivityFeed() {
	   	
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	          ThreadPolicy.Builder().permitAll().build();
    	        StrictMode.setThreadPolicy(policy); 
    	        
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(webserviceURL);
        
        try {
          HttpResponse response = client.execute(httpGet);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line);
            }
          } else {
            Log.e(BlogDetailsFragment.class.toString(), "Failed to download file");
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return builder.toString();
      }
	
	
	public void getActivityItem(JSONArray jsonArray){

        int size = jsonArray.length();
        try{
	        for (int i = size-1; i >=0; i--) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);

	        	activityItem.setCoverUrl(jsonObject.getString("cover_url"));
	        	activityItem.setContent(jsonObject.getString("content"));
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }		
    }
	
	public void updateLayout(){
		//TextView refNoText = (TextView)this.getView().findViewById(R.id.refNo);
		   
		titleText.setText(activityItem.getLast_post_title());
		post_date.setText(activityItem.getLast_post_time());
		blogger_name.setText(activityItem.getUser_name());
        content.setText(activityItem.getContent());
        
        if(activityItem.getCoverUrl()!=null && !activityItem.getCoverUrl().equals("null") && !activityItem.getCoverUrl().isEmpty())
       		ImageLoader.getInstance().displayImage(this.getActivity().getString(R.string.web_url)+"/"+activityItem.getCoverUrl(), imageView);
       	else
       		ImageLoader.getInstance().displayImage("", imageView);
	}
	
	
private class GetActivityItemTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	if(this.isCancelled())
        		return "";
        	
        	String para = "?action=searchBlogContent&refNo=" + activityItem.getRefNo();
			webserviceURL = getActivity().getResources().getString(R.string.WEBSERVICE_BLOG)+para;
			String readTwitterFeed = readActivityFeed();
			
			if(readTwitterFeed==null || readTwitterFeed.equals(""))
				return "";
			
            try {
              jsonArray = new JSONArray(readTwitterFeed);
            } catch (Exception e) {
              e.printStackTrace();
            }

            getActivityItem(jsonArray);
            
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();      
        	
        	if(this.isCancelled())
        		return;
        	
        	updateLayout();
        	linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        }
        
        
      }
    
	

}
