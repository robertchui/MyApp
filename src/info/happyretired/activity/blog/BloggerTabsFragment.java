package info.happyretired.activity.blog;

import info.happyretired.adapter.BlogPostListAdapter;
import info.happyretired.adapter.BloggerListAdapter;
import info.happyretired.adapter.EventListAdapter;
import info.happyretired.adapter.ForumCategoryListAdapter;
import info.happyretired.adapter.ForumListAdapter;
import info.happyretired.adapter.ForumMessageAdapter;
import info.happyretired.communicator.BlogCommunicator;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.string;
import info.happyretired.ult.BlogUtil;
import info.happyretired.ult.CommonConstant;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class BloggerTabsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener   {
	
	private BloggerListAdapter mAdapter;
	private View mRoot;
	private ArrayList mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String mAction;
	private JSONArray jsonArray;
	BlogCommunicator communicator;
	GridView list;
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;
	String featured;
	
	public BloggerTabsFragment(){}
	
	
		
	public String getFeatured() {
		return featured;
	}



	public void setFeatured(String featured) {
		this.featured = featured;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		
		mRoot = inflater.inflate(R.layout.fragment_blogger, container,
                false);
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		list = (GridView) mRoot.findViewById(R.id.gridlist);
		communicator = (BlogCommunicator)this.getActivity();
		
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new BloggerListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
        mAdapter.notifyDataSetChanged();
        list.invalidateViews();
        list.setAdapter(mAdapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				
				
				communicator.respond(position, mlist);
			}
		});
		
		/*
		mAdapter = new BloggerListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		list.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
        list.invalidateViews();
        */
		
        return mRoot;
    }
	
	@Override public void onRefresh() {
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
	    	task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new BloggerListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
        mAdapter.notifyDataSetChanged();
        list.invalidateViews();
        list.setAdapter(mAdapter);
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


	public String getAction() {
		return mAction;
	}


	public void setAction(String mAction) {
		this.mAction = mAction;
	}


    protected void getCategory(JSONArray jsonArray){
    	mlist = new ArrayList();    
        int size = jsonArray.length();
        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        Blogger activityItem = new Blogger();
    	        assignToItem(activityItem, i, jsonObject);
    	        mlist.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
 
    
    public void assignToItem(Blogger activityItem, int i, JSONObject jsonObject) throws Exception{
    	
    	activityItem.setUserid(jsonObject.getString("userid"));
    	activityItem.setUser_name(jsonObject.getString("user_name"));
    	activityItem.setBlog_name(jsonObject.getString("blog_name"));
    	activityItem.setLast_post_title(jsonObject.getString("last_post_title"));
    	activityItem.setLast_post_time(jsonObject.getString("last_post_time"));
    	activityItem.setAvatar_url(jsonObject.getString("avatar_url"));
    	activityItem.setTotal_article(jsonObject.getString("total_article"));
    	activityItem.setSelf_intro(jsonObject.getString("self_intro"));
    	activityItem.setUpdate_remark(jsonObject.getString("update_remark"));
    	
    }
    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	
        	if(isCancelled())
        	{
        	    return "";
        	}
            
            BlogUtil util = new BlogUtil();
        	jsonArray = util.searchBlogger(getFeatured());
            getCategory(jsonArray);
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();       
        	super.onPostExecute(result);
        	
        	//swipeLayout.setRefreshing(false);
            mAdapter = new BloggerListAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
            mAdapter.notifyDataSetChanged();
            list.invalidateViews();
            list.setAdapter(mAdapter);
    		
            linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        }
        
        
      }
    
   

}


