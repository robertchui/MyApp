package info.happyretired.activity.forum;

import info.happyretired.adapter.EventListAdapter;
import info.happyretired.adapter.ForumListAdapter;
import info.happyretired.adapter.ForumMessageAdapter;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.ForumTopicItem;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ForumDetailsFragment extends ListFragment {
	
	private ForumMessageAdapter mAdapter;
	private View mRoot;
	private ArrayList mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String categoryId;
	private JSONArray jsonArray;
	ForumCommunicator communicator;
	ListView list;
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;
	ForumTopicItem topicItem;
	TextView topic;
	View mHeaderView;
	
	
	
	public ForumTopicItem getTopicItem() {
		return topicItem;
	}


	public void setTopicItem(ForumTopicItem topicItem) {
		this.topicItem = topicItem;
	}


	public ForumDetailsFragment(){}
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mRoot = inflater.inflate(R.layout.fragment_forum_details, container,
                false);
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new ForumMessageAdapter(this.getActivity(), android.R.layout.simple_list_item_single_choice, mlist);
		setListAdapter(mAdapter);	
		
        return mRoot;
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
		
		/*
		ForumTopicItem item=null;
		ArrayList list = new ArrayList();
		list.add(mlist.get(position));
		
		
		
		communicator.respond(position, list);
		*/
		
		
	}

	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String readActivityFeed() {
    	   	
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	          ThreadPolicy.Builder().permitAll().build();
    	        StrictMode.setThreadPolicy(policy); 
    	        
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(getActivity().getResources().getString(R.string.WEBSERVICE_FORUM)+"?action=getMessageByTopic&thread_id="+ topicItem.getId());
        
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
            Log.e(ForumDetailsFragment.class.toString(), "Failed to download file");
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
    
      
    protected void getItem(JSONArray jsonArray){
    	mlist = new ArrayList();   
    	topicItem.setListType("Header");
    	mlist.add(topicItem);
    	
        int size = jsonArray.length();
        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	
	        		ForumTopicItem activityItem = new ForumTopicItem();
    	        	assignToItem(activityItem, i, jsonObject);
    	        	mlist.add(activityItem);
	
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
 
    
    public void assignToItem(ForumTopicItem activityItem, int i, JSONObject jsonObject) throws Exception{
    	
    	activityItem.setThread(topicItem.getId());
    	activityItem.setId(jsonObject.getString("id"));
    	activityItem.setSubject(jsonObject.getString("subject"));
    	activityItem.setMessage(jsonObject.getString("message"));
    	activityItem.setLast_post_userid(jsonObject.getString("last_post_userid"));
    	activityItem.setLast_post_guest_name(jsonObject.getString("last_post_guest_name"));
    	activityItem.setLast_post_time(jsonObject.getString("last_post_time"));
    	activityItem.setLast_avatar_url(jsonObject.getString("last_avatar_url"));
    	
    }
    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	/*
        	if(isCancelled())
        	{
        	    return "";
        	}
        	*/
        	
        	
            String readTwitterFeed = readActivityFeed();
            if(readTwitterFeed==null || readTwitterFeed.equals("")){
            	 return "";
            }
            
            try {
              jsonArray = new JSONArray(readTwitterFeed);
            } catch (Exception e) {
              e.printStackTrace();
            }

           
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
            mAdapter = new ForumMessageAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
            setListAdapter(mAdapter);
            linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        }
        
        
      }
    
   

}


