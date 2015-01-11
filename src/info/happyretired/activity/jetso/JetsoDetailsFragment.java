package info.happyretired.activity.jetso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ortiz.touch.ExtendedViewPager;
import com.ortiz.touch.TouchImageView;

import info.happyretired.activity.ViewPagerExampleActivity;
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.adapter.EventListAdapter;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.JetsoItem;
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
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
public class JetsoDetailsFragment extends Fragment {

	private View mRoot;
	private JetsoItem activityItem;
	ProgressDialog mdialog; 
	private JSONArray jsonArray = new JSONArray();
	String refNo;
	String title;
	String webserviceURL;
	LinearLayout linlaHeaderProgress;
	
	TextView topic1;
	TextView topic2;
	TextView titleText;
	TextView company;
	TextView dateFromText;
	ImageView imageView;
	
	ArrayList  imageViews;
	
	TextView content;
	TextView contact;
	
	TextView dateText;
	TextView timeText;
	TextView locationText;
	TextView addressText;
	TextView feeText;
	TextView categoryText;
	TextView urlText;
	TextView contactNoText;
	Button callButton;
	ImageView icon_call;
	Button calendarButton;
	
	ImageView advimageView;
	
	private int page;
	private int totalPage;
	GetActivityItemTask task ;
	
	void setPageDetails(int page, int totalPage) {
		this.page = page;
		this.totalPage = totalPage;
	}
	
	public JetsoDetailsFragment() {
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
			mRoot = inflater.inflate(R.layout.fragment_jetso_details, container,
			        false);

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		topic1 = (TextView)mRoot.findViewById(R.id.contentTitle);
		topic2 = (TextView)mRoot.findViewById(R.id.contentTitle2);
		titleText = (TextView)mRoot.findViewById(R.id.title);
		company = (TextView)mRoot.findViewById(R.id.company);
		dateFromText = (TextView)mRoot.findViewById(R.id.dateFromText);
        imageView = (ImageView) mRoot.findViewById(R.id.imageView1);
        imageView.setOnClickListener(new OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), ViewPagerExampleActivity.class);
                mainIntent.putExtra("url", getResources().getString(R.string.web_url)+activityItem.getImageURL());
                startActivity(mainIntent);
            }
        });
        
        imageViews = new ArrayList();
        
        
        
        
        advimageView = (ImageView) mRoot.findViewById(R.id.advertistment);
        
        content = (TextView)mRoot.findViewById(R.id.content);
        contact = (TextView)mRoot.findViewById(R.id.content2);
        dateText= (TextView)mRoot.findViewById(R.id.TextView02);
    	timeText= (TextView)mRoot.findViewById(R.id.TextView03);
    	locationText= (TextView)mRoot.findViewById(R.id.TextView05);
    	
    	addressText= (TextView)mRoot.findViewById(R.id.TextView06);
    	feeText= (TextView)mRoot.findViewById(R.id.TextView08);
    	categoryText= (TextView)mRoot.findViewById(R.id.TextView10);
    	urlText= (TextView)mRoot.findViewById(R.id.TextView11);
    	contactNoText= (TextView)mRoot.findViewById(R.id.contact_no);
    	callButton = (Button) mRoot.findViewById(R.id.buttonCall);
    	calendarButton = (Button) mRoot.findViewById(R.id.buttonAddCalendar);
    	icon_call= (ImageView) mRoot.findViewById(R.id.icon_call);
        //updateLayout();
    	
    	ExtendedViewPager mViewPager = (ExtendedViewPager) mRoot.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());
		
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

	public void changeData(JetsoItem in){
	//public void changeData(String []in){
		activityItem = in;
		
		refNo = in.getRefNo();
		title = in.getTitle();
		
		
		
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
            Log.e(JetsoDetailsFragment.class.toString(), "Failed to download file");
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
	        	activityItem.assignToItem(i, jsonObject);
    	        	//activityItem = new ActivityItem();
    	        	/*
    	        	activityItem.setRefNo(jsonObject.getString("refNo"));
    	        	activityItem.setTitle(jsonObject.getString("title"));
    	        	activityItem.setDateFrom(jsonObject.getString("dateFrom"));
    	        	activityItem.setDateTo(jsonObject.getString("dateTo"));
    	        	activityItem.setTimeFrom(jsonObject.getString("timeFrom"));
    	        	activityItem.setTimeTo(jsonObject.getString("timeTo"));
    	        	*/
    	        	activityItem.setImageURL(jsonObject.getString("imageURL"));
    	        	activityItem.setContent(jsonObject.getString("content"));
    	        	activityItem.setContact(jsonObject.getString("contact"));
    	        	((JetsoDetailsActivity)this.getActivity()).updateShareButton();
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }		
    }
	
	public void updateLayout(){
		//TextView refNoText = (TextView)this.getView().findViewById(R.id.refNo);
		   
        
        titleText.setText(activityItem.getTitle());
        company.setText(activityItem.getCompany_name());
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				//.showImageForEmptyUri(R.drawable.ic_launcher) 
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getActivity())	
				.defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);
        //ImageLoader.getInstance().displayImage(getResources().getString(R.string.web_url)+activityItem.getImageURL(), imageView);
        
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
		        
		        MyLovelyOnClickListener listner = new MyLovelyOnClickListener(i);
		        iv.setOnClickListener(listner);
		        imageViews.add(iv);
	        }
        }
        
        
        if(activityItem.getImageURLs()!=null && activityItem.getImageURLs().length>0){
        	for(int i =0;i<activityItem.getImageURLs().length;i++){
        		ImageLoader.getInstance().displayImage(getResources().getString(R.string.web_url)+activityItem.getImageURLs()[i], (ImageView)imageViews.get(i));
        	}
        }
        
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String fontsize = settings.getString(CommonConstant.FONT_SIZE, CommonConstant.FONT_SIZE_DEFAULT); 
		
		content.setTextSize(Float.parseFloat(fontsize));
		
        if(activityItem.getContent()!=null && !activityItem.getContent().equals("null") && !activityItem.getContent().equals("")){
        	//content.setText(activityItem.getContent()); 
        	Spanned sp = Html.fromHtml(activityItem.getContent().replace("\n", "<br>"), new URLImageParser(null, content, this.getActivity().getApplicationContext()) ,null);
            content.setText(sp);
            content.setMovementMethod(LinkMovementMethod.getInstance());
            content.setSelectAllOnFocus(true);
        	content.setVisibility(View.VISIBLE);
        	topic1.setVisibility(View.VISIBLE   );
        }
        else{
        	content.setVisibility(View.GONE);
        	topic1.setVisibility(View.GONE);
        }
        
        if(activityItem.getContact()!=null && !activityItem.getContact().equals("null") && !activityItem.getContact().equals("")){
        	//contact.setText(activityItem.getContact());
        	Spanned sp = Html.fromHtml(activityItem.getContact().replace("\n", "<br>"), new URLImageParser(null, content, this.getActivity().getApplicationContext()) ,null);
        	contact.setText(sp);
        	contact.setMovementMethod(LinkMovementMethod.getInstance());
        	contact.setSelectAllOnFocus(true);
        	contact.setVisibility(View.VISIBLE);
        	topic2.setVisibility(View.VISIBLE);
        }
        else{
        	contact.setVisibility(View.GONE);
        	topic2.setVisibility(View.GONE);
        }
        
        
        Date convertedFromDate = null;
        Date convertedToDate = null;
        
        DateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
        try{
	        
        	convertedFromDate = (Date) formatter.parse(activityItem.getDateFrom());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        String dateString ="";
        dateString = String.valueOf(convertedFromDate.getMonth()+1)+"る"+activityItem.getDateFrom().substring(6, 8)+"ら";
        dateFromText.setText(dateString);
        
        if(activityItem.getDateTo()!=null && !activityItem.getDateTo().equals("") && !activityItem.getDateTo().equals("00000000")){
        	try{
        		convertedToDate = (Date) formatter.parse(activityItem.getDateTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedToDate.getMonth()+1)+"る"+activityItem.getDateTo().substring(6, 8)+"ら";
        }
        else if(activityItem.getEffectiveTo()!=null && !activityItem.getEffectiveTo().equals("") && !activityItem.getEffectiveTo().equals("00000000")){
        	try{
        		convertedToDate = (Date) formatter.parse(activityItem.getEffectiveTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedToDate.getMonth()+1)+"る"+activityItem.getEffectiveTo().substring(6, 8)+"ら";
        }
               
        dateText.setText(dateString);
        
      //calendar Button
        Calendar startDay = Calendar.getInstance();
        if(convertedFromDate!=null)
        	startDay.setTime(convertedFromDate);
        
        Calendar endDay = Calendar.getInstance();
        if(convertedToDate!=null)
        	endDay.setTime(convertedToDate);
        
        calendarButton.setOnClickListener(new CalendarListener(activityItem.getTitle(), startDay, endDay));

        
    	locationText.setText(activityItem.getLocationDesc());
    	
    	addressText.setText(activityItem.getAddress());
    	categoryText.setText(activityItem.getCategoryDesc());
    	if(activityItem.getUrl()!=null && !activityItem.getUrl().equals("null") && !activityItem.getUrl().equals("")){
    		urlText.setText(activityItem.getUrl());
    	}
    	else{
    		urlText.setVisibility(View.GONE);
    	}
    	
    	if(activityItem.getContact_no()!=null && !activityItem.getContact_no().equals("null") && !activityItem.getContact_no().equals("")){
    		contactNoText.setText(activityItem.getContact_no());
    		callButton.setOnClickListener(new OnClickListener() {
       		 
    			@Override
    			public void onClick(View arg0) {
     
    				Intent callIntent = new Intent(Intent.ACTION_CALL);
    				callIntent.setData(Uri.parse("tel:"+activityItem.getContact_no()));
    				startActivity(callIntent);
     
    			}
     
    		});
    	}
    	else{
    		contactNoText.setVisibility(View.GONE);
    		callButton.setVisibility(View.GONE);
    		icon_call.setVisibility(View.GONE);
    	}
    	
    	 
        //String url = "images/banners/share2.jpg";
        ImageLoader.getInstance().displayImage(this.getActivity().getResources().getString(R.string.web_url)+"/"+activityItem.getAdvertisementImgUrl(), advimageView);
	}
	
	private class CalendarListener implements  OnClickListener{
			String title;
			Calendar startDay;
			Calendar endDay;
			
	     public CalendarListener(String title, Calendar startDay, Calendar endDay) {
	          this.startDay = startDay;
	          this.endDay = endDay;
	          this.title = title;
	     }
	
	     @Override
	     public void onClick(View v)
	     {
	    	 	Calendar cal = Calendar.getInstance();              
				Intent intent = new Intent(Intent.ACTION_EDIT);
				intent.setType("vnd.android.cursor.item/event");
				intent.putExtra("beginTime", startDay.getTimeInMillis());
				intent.putExtra("allDay", false);
				intent.putExtra("endTime", endDay.getTimeInMillis());
				intent.putExtra("title", title);
				startActivity(intent);
	     }	
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
	        	
	        	String para = "?action=getActivityDetails&refNo=" + refNo;
				webserviceURL = getActivity().getResources().getString(R.string.WEBSERVICE_JETSO)+para;
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
	
	public class MyLovelyOnClickListener implements OnClickListener
	{
	
		int index;
		
		public MyLovelyOnClickListener(int index) {
			this.index = index;
		}
		
		public void onClick(View v) {
	        Intent mainIntent = new Intent(getActivity(), ViewPagerExampleActivity.class);
	        mainIntent.putExtra("url", getResources().getString(R.string.web_url)+activityItem.getImageURLs()[index]);
	        startActivity(mainIntent);
	    }
	
	};
	    
	static class TouchImageAdapter extends PagerAdapter {
	
	    private static int[] images = { R.drawable.nature_1};
	
	    @Override
	    public int getCount() {
	    	return images.length;
	    }
	
	    @Override
	    public View instantiateItem(ViewGroup container, int position) {
	        TouchImageView img = new TouchImageView(container.getContext());
	        img.setImageResource(images[position]);
	        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	        return img;
	    }
	
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((View) object);
	    }
	
	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == object;
	    }
	
	}

}
