package info.happyretired.activity.jetso;

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
import info.happyretired.model.JetsoItem;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
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
    	
    	icon_call= (ImageView) mRoot.findViewById(R.id.icon_call);
        //updateLayout();
		
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
        ImageLoader.getInstance().displayImage(getResources().getString(R.string.web_url)+activityItem.getImageURL(), imageView);

        if(activityItem.getContent()!=null && !activityItem.getContent().equals("null") && !activityItem.getContent().equals("")){
        	content.setText(activityItem.getContent()); 
        	content.setVisibility(View.VISIBLE);
        	topic1.setVisibility(View.VISIBLE   );
        }
        else{
        	content.setVisibility(View.GONE);
        	topic1.setVisibility(View.GONE);
        }
        
        if(activityItem.getContact()!=null && !activityItem.getContact().equals("null") && !activityItem.getContact().equals("")){
        	contact.setText(activityItem.getContact());
        	contact.setVisibility(View.VISIBLE);
        	topic2.setVisibility(View.VISIBLE);
        }
        else{
        	contact.setVisibility(View.GONE);
        	topic2.setVisibility(View.GONE);
        }
        
        
        Date convertedDate = null;
        DateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
        try{
	        
	        convertedDate = (Date) formatter.parse(activityItem.getDateFrom());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    
        
        String dateString ="";
        dateString = String.valueOf(convertedDate.getMonth()+1)+"��"+activityItem.getDateFrom().substring(6, 8)+"��";
        dateFromText.setText(dateString);
        if(activityItem.getDateTo()!=null && !activityItem.getDateTo().equals("") && !activityItem.getDateTo().equals("00000000")){
        	try{
    	         convertedDate = (Date) formatter.parse(activityItem.getDateTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedDate.getMonth()+1)+"��"+activityItem.getDateTo().substring(6, 8)+"��";
        }
        else if(activityItem.getEffectiveTo()!=null && !activityItem.getEffectiveTo().equals("") && !activityItem.getEffectiveTo().equals("00000000")){
        	try{
    	         convertedDate = (Date) formatter.parse(activityItem.getEffectiveTo());
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
        	dateString += " - " + String.valueOf(convertedDate.getMonth()+1)+"��"+activityItem.getEffectiveTo().substring(6, 8)+"��";
        }
               
        dateText.setText(dateString);

        
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
    
	

}