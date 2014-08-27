package info.happyretired.activity.job;

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
import info.happyretired.model.JobItem;
import info.happyretired.ult.CommonConstant;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
public class JobDetailsFragment extends Fragment {

	private View mRoot;
	private JobItem activityItem;
	ProgressDialog mdialog; 
	private JSONArray jsonArray = new JSONArray();
	String refNo;
	String webserviceURL;
		
	TextView post_date;
	TextView company;
	TextView company_info;
	TextView title;
	
	TextView jobDesc1;
	TextView jobDesc2;
	TextView jobDesc3;
	TextView jobDesc4;
	TextView jobDesc5;
	
	TextView jobReq1;
	TextView jobReq2;
	TextView jobReq3;
	TextView jobReq4;
	TextView jobReq5;
	
	TextView applyDesc;
	
	TextView text1;
	TextView text2;
	TextView text3;
	TextView text4;
	TextView text5;
	TextView text6;
	TextView text7;
	TextView text8;
	TextView text9;
	TextView text10;
	TextView text11;
	TextView text12;
	TextView text13;
	TextView text14;
	TextView text15;
	TextView text16;
	TextView text17;
	TextView text18;
	TextView text19;
	TextView text20;
	TextView text21;
	TextView text22;
	
	ImageView imageView;
	
	private int page;
	private int totalPage;
	
	void setPageDetails(int page, int totalPage) {
		this.page = page;
		this.totalPage = totalPage;
	}
	
	public JobDetailsFragment() {
		// Required empty public constructor
	}


	
	
	public JobItem getActivityItem() {
		return activityItem;
	}

	public void setActivityItem(JobItem activityItem) {
		this.activityItem = activityItem;
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
			mRoot = inflater.inflate(R.layout.fragment_job_details, container,
			        false);

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		post_date = (TextView)mRoot.findViewById(R.id.post_date);
		company= (TextView)mRoot.findViewById(R.id.company);
		company_info = (TextView)mRoot.findViewById(R.id.company_info);
		title = (TextView)mRoot.findViewById(R.id.title);
		
		jobDesc1 = (TextView)mRoot.findViewById(R.id.jobDesc1);
		jobDesc2 = (TextView)mRoot.findViewById(R.id.jobDesc2);
		jobDesc3 = (TextView)mRoot.findViewById(R.id.jobDesc3);
		jobDesc4 = (TextView)mRoot.findViewById(R.id.jobDesc4);
		jobDesc5 = (TextView)mRoot.findViewById(R.id.jobDesc5);
		
		jobReq1 = (TextView)mRoot.findViewById(R.id.jobReq1);
		jobReq2 = (TextView)mRoot.findViewById(R.id.jobReq2);
		jobReq3 = (TextView)mRoot.findViewById(R.id.jobReq3);
		jobReq4 = (TextView)mRoot.findViewById(R.id.jobReq4);
		jobReq5 = (TextView)mRoot.findViewById(R.id.jobReq5);
		
		applyDesc = (TextView)mRoot.findViewById(R.id.applyDesc);
		imageView  = (ImageView)mRoot.findViewById(R.id.companyImage);

		
		 DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
					//.showImageForEmptyUri(R.drawable.ic_launcher) 
					.build();
			
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getActivity())	
					.defaultDisplayImageOptions(defaultOptions)
					.build();
			ImageLoader.getInstance().init(config);
			
		if(activityItem.getCompanyImgUrl()!=null && !activityItem.getCompanyImgUrl().equals("null") && !activityItem.getCompanyImgUrl().isEmpty())
       		ImageLoader.getInstance().displayImage(this.getActivity().getString(R.string.web_url)+"/"+activityItem.getCompanyImgUrl(), imageView);
       	else{
       		//ImageLoader.getInstance().displayImage("", imageView);
       		imageView.setVisibility(View.GONE);
       	}
		
		
		text1 = (TextView)mRoot.findViewById(R.id.text1);
		text2 = (TextView)mRoot.findViewById(R.id.text2);
		text3 = (TextView)mRoot.findViewById(R.id.text3);
		text4 = (TextView)mRoot.findViewById(R.id.text4);
		text5 = (TextView)mRoot.findViewById(R.id.text5);
		text6 = (TextView)mRoot.findViewById(R.id.text6);
		text7 = (TextView)mRoot.findViewById(R.id.text7);
		text8 = (TextView)mRoot.findViewById(R.id.text8);
		text9 = (TextView)mRoot.findViewById(R.id.text9);
		text10 = (TextView)mRoot.findViewById(R.id.text10);
		text11 = (TextView)mRoot.findViewById(R.id.text11);
		text12 = (TextView)mRoot.findViewById(R.id.text12);
		text13 = (TextView)mRoot.findViewById(R.id.text13);
		text14 = (TextView)mRoot.findViewById(R.id.text14);
		text15 = (TextView)mRoot.findViewById(R.id.text15);
		text16 = (TextView)mRoot.findViewById(R.id.text16);
		text17 = (TextView)mRoot.findViewById(R.id.text17);
		text18 = (TextView)mRoot.findViewById(R.id.text18);
		text19 = (TextView)mRoot.findViewById(R.id.text19);
		text20 = (TextView)mRoot.findViewById(R.id.text20);
		text21 = (TextView)mRoot.findViewById(R.id.text21);
		text22 = (TextView)mRoot.findViewById(R.id.text22);
		
		
		
		post_date.setText(activityItem.getPostdate());
		
		
		if(activityItem.getCompanyUrl()==null || activityItem.getCompanyUrl().equals("") || activityItem.getCompanyUrl().equals("null"))
			company.setText(activityItem.getCompanyName());
		else{
			String url ="<a href=\""+activityItem.getCompanyUrl()+"\">"+activityItem.getCompanyName()+"</a>";
			//
			company.setText(Html.fromHtml(url));
			company.setMovementMethod(LinkMovementMethod.getInstance());
		}
		
		
		if(activityItem.getCompanyDesc()==null || activityItem.getCompanyDesc().equals("") || activityItem.getCompanyDesc().equals("null"))
			company_info.setVisibility(View.GONE);
		else
			company_info.setText(activityItem.getCompanyDesc());
		
		title.setText(activityItem.getTitle());
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String fontsize = settings.getString(CommonConstant.FONT_SIZE, CommonConstant.FONT_SIZE_DEFAULT); 
			
		jobDesc1.setTextSize(Float.parseFloat(fontsize));
		jobDesc2.setTextSize(Float.parseFloat(fontsize));
		jobDesc3.setTextSize(Float.parseFloat(fontsize));
		jobDesc4.setTextSize(Float.parseFloat(fontsize));
		jobDesc5.setTextSize(Float.parseFloat(fontsize));
		jobReq1.setTextSize(Float.parseFloat(fontsize));
		jobReq2.setTextSize(Float.parseFloat(fontsize));
		jobReq3.setTextSize(Float.parseFloat(fontsize));
		jobReq4.setTextSize(Float.parseFloat(fontsize));
		jobReq5.setTextSize(Float.parseFloat(fontsize));
			
		if(activityItem.getJobDesc1()==null || activityItem.getJobDesc1().equals("") || activityItem.getJobDesc1().equals("null"))
			jobDesc1.setVisibility(View.GONE);
		else
			jobDesc1.setText(activityItem.getJobDesc1());
		
		if(activityItem.getJobDesc2()==null || activityItem.getJobDesc2().equals("") || activityItem.getJobDesc2().equals("null"))
			jobDesc2.setVisibility(View.GONE);
		else
			jobDesc2.setText(activityItem.getJobDesc2());
		
		if(activityItem.getJobDesc3()==null || activityItem.getJobDesc3().equals("") || activityItem.getJobDesc3().equals("null"))
			jobDesc3.setVisibility(View.GONE);
		else
			jobDesc3.setText(activityItem.getJobDesc3());
		
		if(activityItem.getJobDesc4()==null || activityItem.getJobDesc4().equals("") || activityItem.getJobDesc4().equals("null"))
			jobDesc4.setVisibility(View.GONE);
		else
			jobDesc4.setText(activityItem.getJobDesc4());
		
		if(activityItem.getJobDesc5()==null || activityItem.getJobDesc5().equals("") || activityItem.getJobDesc5().equals("null"))
			jobDesc5.setVisibility(View.GONE);
		else
			jobDesc5.setText(activityItem.getJobDesc5());
		
		
		
		if(activityItem.getJobReq1()==null || activityItem.getJobReq1().equals("") || activityItem.getJobReq1().equals("null"))
			jobReq1.setVisibility(View.GONE);
		else
			jobReq1.setText(activityItem.getJobReq1());
		
		if(activityItem.getJobReq2()==null || activityItem.getJobReq2().equals("") || activityItem.getJobReq2().equals("null"))
			jobReq2.setVisibility(View.GONE);
		else
			jobReq2.setText(activityItem.getJobReq2());
		
		if(activityItem.getJobReq3()==null || activityItem.getJobReq3().equals("") || activityItem.getJobReq3().equals("null"))
			jobReq3.setVisibility(View.GONE);
		else
			jobReq3.setText(activityItem.getJobReq3());
		
		if(activityItem.getJobReq4()==null || activityItem.getJobReq4().equals("") || activityItem.getJobReq4().equals("null"))
			jobReq4.setVisibility(View.GONE);
		else
			jobReq4.setText(activityItem.getJobReq4());
		
		if(activityItem.getJobReq5()==null || activityItem.getJobReq5().equals("") || activityItem.getJobReq5().equals("null"))
			jobReq5.setVisibility(View.GONE);
		else
			jobReq5.setText(activityItem.getJobReq5());
		
		applyDesc.setText(activityItem.getApplyDesc());
		
		text2.setText(activityItem.getJobcategory_name()+"("+activityItem.getJobnature_name()+")");
		text4.setText(activityItem.getLocationDesc());
		text6.setText(activityItem.getEduDesc());
		text8.setText(activityItem.getWorking_exp());
		text10.setText(activityItem.getWorking_period());
		text12.setText(activityItem.getHour_per_day()+"/"+activityItem.getDay_per_week());
		
		if(activityItem.getSalary()==null || activityItem.getSalary().equals("") || activityItem.getSalary().equals("null")){
			if(activityItem.getDisplayLang().equals("ENG"))
				text14.setText("Negotiable");
			else
				text14.setText("­±Ä³");
		}
		else
			text14.setText(activityItem.getSalary()+"("+activityItem.getSalary_type()+")");
		text16.setText(activityItem.getBenefit());
		text18.setText(activityItem.getEmployment_nature()+","+activityItem.getEmployment_type());
		text20.setText(activityItem.getVacancy());
		text22.setText(activityItem.getEffectiveTo());

		
        return mRoot;
    }

	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void changeData(JobItem in){
	//public void changeData(String []in){
		activityItem = in;
	}
	
}
