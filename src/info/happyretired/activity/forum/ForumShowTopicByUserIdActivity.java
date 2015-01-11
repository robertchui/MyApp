package info.happyretired.activity.forum;

import info.happyretired.activity.CommonTabsActivity;
import info.happyretired.activity.ViewPagerExampleActivity;
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.adapter.ForumListAdapter;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.model.BlogPostItem;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.R;
import info.happyretired.R.color;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.menu;
import info.happyretired.R.string;


import info.happyretired.ult.CommonConstant;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.android.common.view.SlidingTabLayout;
import com.example.androidhive.library.ForumWebserviceUtil;
import com.example.androidhive.library.UserFunctions;
import com.example.androidhive.library.UserWebserviceUtil;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;


public class ForumShowTopicByUserIdActivity  extends ListActivity   {

	ForumListAdapter mAdapter;
	LinearLayout linlaHeaderProgress;
	ArrayList mlist;
	int page = 1;
	GetListTask task;
	ActionBar actionBar = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        //viewPager = (ViewPager)findViewById(R.id.pager);
        //FragmentManager fm = this.getSupportFragmentManager();
        //actionBar.setTitle(getResources().getString(R.string.forum));
        actionBar =  getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        mlist = new ArrayList();
        mAdapter = new ForumListAdapter(this.getApplicationContext(), android.R.layout.simple_list_item_1, mlist);
        //setListAdapter(mAdapter);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        //loginField = (EditText) findViewById(R.id.registerName);
        task = new GetListTask();
        task.execute();
        linlaHeaderProgress.setVisibility(View.GONE);
      
    }
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Tracker tracker = GoogleAnalytics.getInstance(this).getTracker("UA-48863792-2");
		tracker.set(Fields.SCREEN_NAME, this.getClass().getName()+" Screen");
		tracker.send(MapBuilder
			    .createAppView()
			    .build()
			);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
    
    public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, ForumDetailsActivity.class);
		ArrayList in = new ArrayList();
		in.add(mlist.get(position));
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);
    }
    
    protected void getItem(JSONArray jsonArray){
    	mlist = new ArrayList();    
        int size = jsonArray.length();
        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	
	        		ForumTopicItem activityItem = new ForumTopicItem();
	        		activityItem.assignToItem(i, jsonObject);
    	        	mlist.add(activityItem);
	
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }

    private class GetListTask extends AsyncTask<String, Void, String> {
    	
    	JSONArray json;
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		//linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	if(this.isCancelled())
        		return "";
        	
        	UserFunctions userFunction = new UserFunctions();
        	HashMap user = userFunction.getUserDetails(getApplicationContext());
        	
        	ForumWebserviceUtil forumWebserviceUtil = new ForumWebserviceUtil();
            json = forumWebserviceUtil.getRecentReplyTopicsByUserId(Integer.parseInt((String)user.get("uid")), page);
            getItem(json);
            
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getApplicationContext();      
        	
        	if(this.isCancelled())
        		return;

        	//linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        	
        	if(json!=null){
            	try{
            		mAdapter = new ForumListAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
            		mAdapter.notifyDataSetChanged();
            		setListAdapter(mAdapter);
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            	
            }
        }
      }
	
}