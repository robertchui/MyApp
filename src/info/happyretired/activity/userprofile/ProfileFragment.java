package info.happyretired.activity.userprofile;

import info.happyretired.adapter.EventListAdapter;
import info.happyretired.adapter.ForumCategoryListAdapter;
import info.happyretired.adapter.ForumListAdapter;
import info.happyretired.adapter.NavDrawerListAdapter;
import info.happyretired.adapter.ProfileListAdapter;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.communicator.ProfileCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.ult.UserFunctionsUtil;
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
import java.util.HashMap;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class ProfileFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener   {
	
	private NavDrawerListAdapter mAdapter;
	private View mRoot;
	private ArrayList <NavDrawerItem> mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String mAction;
	private JSONArray jsonArray;
	ProfileCommunicator communicator;
	ListView list;
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;
	TextView nameField;
	
	private String[] navMenuTitles;
	
	private UserFunctionsUtil userFunction = new UserFunctionsUtil();
	
	public ProfileFragment(){}
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		
		mRoot = inflater.inflate(R.layout.profile, container,
                false);
		
		nameField = (TextView) mRoot.findViewById(R.id.name);
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		communicator = (ProfileCommunicator)this.getActivity();
		
				

		HashMap user = userFunction.getUserDetails(this.getActivity().getApplicationContext());
		nameField.setText((String)user.get("name"));
		
		mlist = new ArrayList();
    	navMenuTitles = getResources().getStringArray(R.array.profile_items);

    	mlist.add(new NavDrawerItem(navMenuTitles[0],"{fa-user}", "#b22222"));
    	mlist.add(new NavDrawerItem(navMenuTitles[1],"{fa-comments}", "#3f8be1"));
    	mlist.add(new NavDrawerItem(navMenuTitles[2],"{fa-credit-card}", "#f63c2b"));
    	mlist.add(new NavDrawerItem(navMenuTitles[3],"{fa-sign-out}", "#8D8D8D"));
    	
    	mAdapter = new NavDrawerListAdapter(this.getActivity(), mlist);
    	//mAdapter = new ProfileListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		setListAdapter(mAdapter);		
    	/*
		DownloadWebPageTask task = new DownloadWebPageTask();
	    task.execute();
	    */
		
        return mRoot;
    }
	
	@Override public void onRefresh() {
		
		//mAdapter = new ProfileListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		mAdapter = new NavDrawerListAdapter(this.getActivity(), mlist);
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
		
		communicator.goToPage(position);
	}


	public String getAction() {
		return mAction;
	}


	public void setAction(String mAction) {
		this.mAction = mAction;
	}


	public String readActivityFeed() {
    	   	
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	          ThreadPolicy.Builder().permitAll().build();
    	        StrictMode.setThreadPolicy(policy); 
    	        
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(getActivity().getResources().getString(R.string.WEBSERVICE_FORUM)+"?action=getCategory");
        
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
            Log.e(ProfileFragment.class.toString(), "Failed to download file");
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
   
    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	/*
            String readTwitterFeed = readActivityFeed();
            try {
              jsonArray = new JSONArray(readTwitterFeed);
            } catch (Exception e) {
              e.printStackTrace();
            }
            
            getCategory(jsonArray);
            */
        	
        	
        	/*
        	ForumCategoryItem activityItem = new ForumCategoryItem();
        	activityItem.setName("會員證");
        	mlist.add(activityItem);
        	
        	activityItem = new ForumCategoryItem();
        	activityItem.setName("會員優惠");
        	mlist.add(activityItem);
        	
        	activityItem = new ForumCategoryItem();
        	activityItem.setName("登出");
        	mlist.add(activityItem);
            */
        	
        	
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();       
        	super.onPostExecute(result);
        	mAdapter.notifyDataSetInvalidated();
        	//swipeLayout.setRefreshing(false);
            //mAdapter = new ProfileListAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
        	mAdapter = new NavDrawerListAdapter(myContext, mlist);
            setListAdapter(mAdapter);
            linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        }
        
        
      }
    
   

}


