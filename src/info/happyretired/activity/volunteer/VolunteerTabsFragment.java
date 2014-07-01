package info.happyretired.activity.volunteer;

import info.happyretired.adapter.EventListAdapter;
import info.happyretired.adapter.VolunteerListAdapter;
import info.happyretired.communicator.ActivityCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.VolunteerItem;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class VolunteerTabsFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener   {
	
	private VolunteerListAdapter mAdapter;
	private View mRoot;
	private ArrayList mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String mAction;
	private JSONArray jsonArray;
	ActivityCommunicator communicator;
	ListView list;
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;
	final String webserviceURL = "https://www.happy-retired.com/volunteerwebservice";
	String timeofService;
	
	public VolunteerTabsFragment(){}
	
		
	public String getTimeofService() {
		return timeofService;
	}


	public void setTimeofService(String timeofService) {
		this.timeofService = timeofService;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
		mRoot = inflater.inflate(R.layout.fragment_message, container,
                false);
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		communicator = (ActivityCommunicator)this.getActivity();

		if(mlist.size()==0){
			task = new DownloadWebPageTask();
		    task.execute(new String[] { webserviceURL });
		}
		mAdapter = new VolunteerListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		setListAdapter(mAdapter);
		
        return mRoot;
    }
	
	@Override public void onRefresh() {
		if(mlist.size()==0){
			task = new DownloadWebPageTask();
	    	task.execute(new String[] { webserviceURL });
		}
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
		
		ArrayList list = new ArrayList();
		
		for(int i=0;i<mlist.size();i++){
			VolunteerItem item = (VolunteerItem)mlist.get(i);
			String array[] = {item.getRefNo(), item.getTitle()};
			list.add(item);
		}
		
		communicator.respond(position, list);
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
        
        HttpGet httpGet = new HttpGet(getActivity().getResources().getString(R.string.WEBSERVICE_VOLUNTEER)+"?action=getVolunteerList"+"&timeofservice="+timeofService);
        
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
            Log.e(VolunteerTabsFragment.class.toString(), "Failed to download file");
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
    
    protected void getItems(JSONArray jsonArray){
    	mlist = new ArrayList();    
        int size = jsonArray.length();
        try{
	        for (int i = size-1; i >=0; i--) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	VolunteerItem activityItem = new VolunteerItem();
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
            try {
              jsonArray = new JSONArray(readTwitterFeed);
            } catch (Exception e) {
              e.printStackTrace();
            }

            if(jsonArray!=null && jsonArray.length()>0)
            	getItems(jsonArray);
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();       
        	super.onPostExecute(result);
        	mAdapter.notifyDataSetInvalidated();
            mAdapter = new VolunteerListAdapter(myContext, android.R.layout.simple_list_item_1, mlist);
            setListAdapter(mAdapter);
            linlaHeaderProgress.setVisibility(View.GONE);

        }
        
        
      }
    
   

}


