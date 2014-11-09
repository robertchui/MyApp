package info.happyretired.activity.volunteer;

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
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.adapter.EventListAdapter;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.VolunteerItem;
import info.happyretired.ult.CommonConstant;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class VolunteerDetailsFragment extends Fragment {

	private View mRoot;
	private VolunteerItem activityItem;
	ProgressDialog mdialog; 
	private JSONArray jsonArray = new JSONArray();
	String refNo;
	String title;
	String webserviceURL;
	LinearLayout linlaHeaderProgress;
	
	TextView titleText;
	TextView companyNameText;
	ImageView imageView;
	TextView content;
	TextView content2;
	TextView contact;
	
	TextView dateText;
	TextView timeText;
	TextView locationText;
	TextView addressText;
	TextView feeText;
	TextView targetgroupText;
	TextView urlText;
	TextView vacancyText;
	TextView contactNoText;
	Button callButton;
	ImageView icon_call;
	ImageView advimageView;
	
	private int page;
	private int totalPage;
	GetActivityItemTask task ;
	
	void setPageDetails(int page, int totalPage) {
		this.page = page;
		this.totalPage = totalPage;
	}
	
	public VolunteerDetailsFragment() {
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
			mRoot = inflater.inflate(R.layout.fragment_volunteer_details, container,
			        false);

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		titleText = (TextView)mRoot.findViewById(R.id.title);
		companyNameText = (TextView)mRoot.findViewById(R.id.companyName);
        imageView = (ImageView) mRoot.findViewById(R.id.imageView1);
        advimageView = (ImageView) mRoot.findViewById(R.id.advertistment);
        
        imageView.setOnClickListener(new OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), ViewPagerExampleActivity.class);
                mainIntent.putExtra("url", getResources().getString(R.string.web_url)+activityItem.getImageURL());
                startActivity(mainIntent);
            }
        });
        
        content = (TextView)mRoot.findViewById(R.id.content);
        content2 = (TextView)mRoot.findViewById(R.id.content2);
        contact = (TextView)mRoot.findViewById(R.id.content3);
        dateText= (TextView)mRoot.findViewById(R.id.TextView02);
    	timeText= (TextView)mRoot.findViewById(R.id.TextView03);
    	locationText= (TextView)mRoot.findViewById(R.id.TextView05);
    	
    	addressText= (TextView)mRoot.findViewById(R.id.TextView06);
    	
    	targetgroupText= (TextView)mRoot.findViewById(R.id.TextView08);
    	vacancyText= (TextView)mRoot.findViewById(R.id.TextView10);
    	urlText= (TextView)mRoot.findViewById(R.id.TextView11);
    	contactNoText= (TextView)mRoot.findViewById(R.id.contact_no);
    	callButton = (Button) mRoot.findViewById(R.id.buttonCall);
    	icon_call= (ImageView) mRoot.findViewById(R.id.icon_call);
        //updateLayout();
		
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress2);
		if(jsonArray.length()==0){
			task = new GetActivityItemTask();
		    task.execute(new String[] { "https://www.happy-retired.com/volunteerwebservice" });
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

	public void changeData(VolunteerItem in){
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
            Log.e(VolunteerDetailsFragment.class.toString(), "Failed to download file");
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
    	        	activityItem.setContent2(jsonObject.getString("content2"));
    	        	activityItem.setContact(jsonObject.getString("contact"));
    	        	((VolunteerDetailsActivity)this.getActivity()).updateShareButton();
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }		
    }
	
	public void updateLayout(){
		//TextView refNoText = (TextView)this.getView().findViewById(R.id.refNo);
		   
        
        titleText.setText(activityItem.getTitle());
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				//.showImageForEmptyUri(R.drawable.ic_launcher) 
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getActivity())	
				.defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);
        
        if(activityItem.getImageURL()!=null && !activityItem.getImageURL().equals(""))
        	ImageLoader.getInstance().displayImage(getResources().getString(R.string.web_url)+activityItem.getImageURL(), imageView);
        else
        	imageView.setVisibility(View.INVISIBLE);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String fontsize = settings.getString(CommonConstant.FONT_SIZE, CommonConstant.FONT_SIZE_DEFAULT); 
			
		content.setTextSize(Float.parseFloat(fontsize));
		content2.setTextSize(Float.parseFloat(fontsize));
		contact.setTextSize(Float.parseFloat(fontsize));
		
        content.setText(activityItem.getContent()); 
        content2.setText(activityItem.getContent2()); 
        contact.setText(activityItem.getContact());
        
        
        Date convertedDate = null;
        DateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
        try{
	        
	        convertedDate = (Date) formatter.parse(activityItem.getDateFrom());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    
        
        String dateString ="";
        dateString = String.valueOf(convertedDate.getMonth()+1)+"る"+activityItem.getDateFrom().substring(6, 8)+"ら";
        companyNameText.setText(activityItem.getCompanyName());
        
        if(activityItem.getDateTo()!=null && !activityItem.getDateTo().equals("") && !activityItem.getDateTo().equals("00000000")){
        	try{
    	         convertedDate = (Date) formatter.parse(activityItem.getDateTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedDate.getMonth()+1)+"る"+activityItem.getDateTo().substring(6, 8)+"ら";
        }
        else if(activityItem.getEffectiveTo()!=null && !activityItem.getEffectiveTo().equals("") && !activityItem.getEffectiveTo().equals("00000000")){
        	try{
    	         convertedDate = (Date) formatter.parse(activityItem.getEffectiveTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedDate.getMonth()+1)+"る"+activityItem.getEffectiveTo().substring(6, 8)+"ら";
        }
               
        dateText.setText(dateString);
        
        String timeString ="";
        timeString = activityItem.getTimeFrom();
        if(activityItem.getTimeFrom()!=null && !activityItem.getTimeFrom().equals(""))
        	timeString = activityItem.getTimeFrom();
        
        if(activityItem.getTimeTo()!=null && !activityItem.getTimeTo().equals(""))
        	timeString = timeString + " - " + activityItem.getTimeTo();
        
        timeText.setText(timeString);
        
    	locationText.setText(activityItem.getLocationDesc());
    	
    	addressText.setText(activityItem.getAddress());
    	
    	
    	targetgroupText.setText(activityItem.getTargetgroupDesc());
    	vacancyText.setText(activityItem.getVacancy());
    	urlText.setText(activityItem.getUrl());
    	
    	if(activityItem.getContact_no()!=null && !activityItem.getContact_no().equals("null")){
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
	
	
private class GetActivityItemTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	/*
        	if(this.isCancelled())
        		return "";
        		*/
        	
        	String para = "?action=getVolunteerDetails&refNo=" + refNo;
			webserviceURL = getActivity().getResources().getString(R.string.WEBSERVICE_VOLUNTEER)+para;
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
