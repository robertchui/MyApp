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

import info.happyretired.activity.ViewPagerExampleActivity;
import info.happyretired.activity.jetso.JetsoDetailsFragment.MyLovelyOnClickListener;
import info.happyretired.adapter.EventListAdapter;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.ult.BlogUtil;
import info.happyretired.ult.CommonConstant;
import info.happyretired.ult.URLImageParser;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
	
	ImageView advimageView;
	ArrayList  imageViews;
	
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
        imageView.setOnClickListener(new OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), ViewPagerExampleActivity.class);
                mainIntent.putExtra("url", getResources().getString(R.string.web_url)+"/"+activityItem.getCoverUrl());
                startActivity(mainIntent);
            }
        });
        imageViews = new ArrayList();
        
        
        advimageView = (ImageView) mRoot.findViewById(R.id.advertistment); 
        
        content = (TextView)mRoot.findViewById(R.id.content);
		
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress2);
		if(jsonArray.length()==0){
			task = new GetActivityItemTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		else{
			updateLayout();
		}
	

		
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
	
	
	public void getActivityItem(JSONArray jsonArray){

        int size = jsonArray.length();
        try{
	        for (int i = size-1; i >=0; i--) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	activityItem.assignToItem(i, jsonObject);
	        	activityItem.setCoverUrl(jsonObject.getString("cover_url"));
	        	activityItem.setContent(jsonObject.getString("content"));
	        	((BlogDetailsActivity)this.getActivity()).updateShareButton();
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
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String fontsize = settings.getString(CommonConstant.FONT_SIZE, CommonConstant.FONT_SIZE_DEFAULT); 
		
		content.setTextSize(Float.parseFloat(fontsize));
        //content.setText(activityItem.getContent());
		
        Spanned sp = Html.fromHtml(activityItem.getContent().replace("\n", "<br>"), new URLImageParser(null, content, this.getActivity().getApplicationContext()) ,null);
        content.setText(sp);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        //content.setSelectAllOnFocus(true);
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				//.showImageForEmptyUri(R.drawable.ic_launcher) 
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getActivity())	
				.defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);
		
		 if(activityItem.getImageURLs()!=null && activityItem.getImageURLs().length>0){
	        	int lastId = R.id.imageView1;
		        for(int i =0;i<activityItem.getImageURLs().length;i++){
			        //ImageView iv = new ImageView(this.getActivity());
			        my.view.DynamicImageView iv = new my.view.DynamicImageView(this.getActivity().getApplicationContext(), null);
			        LinearLayout rl = (LinearLayout) mRoot.findViewById(R.id.linearlayout);
			        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			            RelativeLayout.LayoutParams.MATCH_PARENT,
			            RelativeLayout.LayoutParams.WRAP_CONTENT);
			        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			        rl.addView(iv, lp);
			        
			        MyLovelyOnClickListener listner = new MyLovelyOnClickListener(activityItem.getImageURLs()[i]);
			        iv.setOnClickListener(listner);
			        imageViews.add(iv);
		        }
	        }
	        
	        
	        if(activityItem.getImageURLs()!=null && activityItem.getImageURLs().length>0){
	        	for(int i =0;i<activityItem.getImageURLs().length;i++){
	        		ImageLoader.getInstance().displayImage(getResources().getString(R.string.web_url)+activityItem.getImageURLs()[i], (ImageView)imageViews.get(i));
	        	}
	        }
	        
        /*
        if(activityItem.getCoverUrl()!=null && !activityItem.getCoverUrl().equals("null") && !activityItem.getCoverUrl().isEmpty())
       		ImageLoader.getInstance().displayImage(this.getActivity().getString(R.string.web_url)+"/"+activityItem.getCoverUrl(), imageView);
       	else
       		ImageLoader.getInstance().displayImage("", imageView);
       	*/
       
        //String url = "images/banners/share2.jpg";
        ImageLoader.getInstance().displayImage(this.getActivity().getResources().getString(R.string.web_url)+"/"+activityItem.getAdvertisementImgUrl(), advimageView);
        
        if(activityItem.getAdvertisementUrl()!=null && !activityItem.getAdvertisementUrl().equals("")){
        	        	        	
        	advimageView.setOnClickListener(new OnClickListener() {
                // Start new list activity
                public void onClick(View v) {
                    Intent mainIntent = new Intent("android.intent.action.VIEW", Uri.parse(activityItem.getAdvertisementUrl()));
                    startActivity(mainIntent);
                }
            });
        }
	}
	
	
	public class MyLovelyOnClickListener implements OnClickListener
	{

		int index;
		String url;
		
		public MyLovelyOnClickListener(String url) {
			//this.index = index;
			this.url = url;
		}
		
		public void onClick(View v) {
	        Intent mainIntent = new Intent(getActivity(), ViewPagerExampleActivity.class);
	        //mainIntent.putExtra("url", getResources().getString(R.string.web_url)+activityItem.getImageURLs()[index]);
	        mainIntent.putExtra("url", getResources().getString(R.string.web_url)+url);
	        startActivity(mainIntent);
	    }

	};	
	
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
        	
        	BlogUtil util = new BlogUtil();
        	jsonArray = util.searchBlogContent(activityItem.getRefNo());
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
