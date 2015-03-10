package info.happyretired.activity.blog;

import info.happyretired.adapter.BlogPostListAdapter;
import info.happyretired.adapter.EventListAdapter;
import info.happyretired.adapter.ForumListAdapter;
import info.happyretired.communicator.BlogCommunicator;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.BlogPostItem;
import info.happyretired.model.Blogger;
import info.happyretired.ult.BlogUtil;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class BlogTabsFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener   {
	
	private BlogPostListAdapter mAdapter;
	private View mRoot;
	private ArrayList mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String categoryId;
	private JSONArray jsonArray;
	BlogCommunicator communicator;
	ListView list;
	ListView listView;
	SwipeRefreshLayout swipeLayout;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;

	
	public BlogTabsFragment(){}
	
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		
		mRoot = inflater.inflate(R.layout.fragment_message, container, false);
		//mRoot = inflater.inflate(R.layout.fragment_message_swipe, container, false);
		
		
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		communicator = (BlogCommunicator)this.getActivity();
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new BlogPostListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		setListAdapter(mAdapter);			

		/*  swipe view */
		/*
		swipeLayout = (SwipeRefreshLayout) mRoot.findViewById(R.id.swipe_container); 
		swipeLayout.setEnabled(false);
		
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	        @Override
	        public void onRefresh() {
	        	swipeLayout.setRefreshing(true);
	            ( new Handler()).postDelayed(new Runnable() {
	                @Override
	                public void run() {
	                	swipeLayout.setRefreshing(false);
	 
	                }
	            }, 3000);
	        }
	    });
	    */
		
		/*
		this.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
	        @Override
	        public void onScrollStateChanged(AbsListView absListView, int i) {
	 
	        }
	 
	        @Override
	        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	                if (firstVisibleItem == 0)
	                	swipeLayout.setEnabled(true);
	                else
	                	swipeLayout.setEnabled(false);
	        }
	    });
	    */
	
	 
		
		return mRoot;
    }
	

	
	@Override public void onRefresh() {
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
	    	task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new BlogPostListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(task != null)
			task.cancel(true);
		
		linlaHeaderProgress.setVisibility(View.GONE);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Blogger item=null;
		ArrayList list = new ArrayList();
		//list.add(mlist.get(position));
		
		
		for(int i=0;i<mlist.size();i++){
			item = (Blogger)mlist.get(i);
			list.add(item);
		}
		
		communicator.respondReadArticle(position, list);
		
		
	}

	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

      
    protected void getItem(JSONArray jsonArray){
    	mlist = new ArrayList();    
        int size = jsonArray.length();
        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	
	        		Blogger activityItem = new Blogger();
	        		activityItem.assignToItem(i, jsonObject);
    	        	mlist.add(activityItem);
	
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
 

    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
            BlogUtil util = new BlogUtil();
        	jsonArray = util.searchBlog(null, categoryId);
        	
            if(jsonArray!=null && jsonArray.length()>0)
            	getItem(jsonArray);
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();       
        	super.onPostExecute(result);
        	mAdapter.notifyDataSetInvalidated();
        	//swipeLayout.setRefreshing(false);
            mAdapter = new BlogPostListAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
            setListAdapter(mAdapter);
            linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        }
        
        
      }
    
   

}


