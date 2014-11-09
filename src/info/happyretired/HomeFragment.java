package info.happyretired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import info.happyretired.activity.blog.BloggerTabsFragment;
import info.happyretired.adapter.BloggerListAdapter;
import info.happyretired.adapter.ForumCategoryListAdapter;
import info.happyretired.adapter.FrontPageAdapter;
import info.happyretired.communicator.BlogCommunicator;
import info.happyretired.communicator.ForumCommunicator;
import info.happyretired.communicator.FrontCommunicator;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.JetsoItem;
import info.happyretired.model.JobItem;
import info.happyretired.model.VolunteerItem;
import info.happyretired.R;
import info.happyretired.ult.CommonConstant;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class HomeFragment extends Fragment {
	
	private FrontPageAdapter mAdapter;
	private FrontPageAdapter mAdapter2;
	private FrontPageAdapter mAdapter3;
	private FrontPageAdapter mAdapter4;
	private FrontPageAdapter mAdapter5;
	private FrontPageAdapter mAdapter6;
	private FrontPageAdapter mAdapter7;
	private FrontPageAdapter mAdapter8;
	FrontCommunicator communicator;
	
	private View mRoot;
	private ArrayList mlist = new ArrayList();  
	ProgressDialog mdialog; 
	private String mAction;
	private JSONArray jsonArray;
	
	GridView list;
	GridView list2;
	GridView list3;
	//GridView list4;
	GridView list5;
	GridView list6;
	GridView list7;
	//GridView list8;
	
	Button button1;
	Button button2;
	Button button3;
	//Button button4;
	Button button5;
	Button button6;
	Button button7;
	//Button button8;
	
	RelativeLayout rLayout1;
	RelativeLayout rLayout2;
	RelativeLayout rLayout3;
	//RelativeLayout rLayout4;
	RelativeLayout rLayout5;
	RelativeLayout rLayout6;
	RelativeLayout rLayout7;
	//RelativeLayout rLayout8;
	
	LinearLayout linear;
	
	
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	LinearLayout linlaHeaderProgress;
	DownloadWebPageTask task;


	private ArrayList featuredVolunteers = new ArrayList();  
	private ArrayList featuredEvents = new ArrayList();  
	private ArrayList comingEvents = new ArrayList();  
	private ArrayList featuredBlogs = new ArrayList();  
	private ArrayList latestJobs = new ArrayList();  
	private ArrayList latestForums = new ArrayList();
	private ArrayList latestJetsos = new ArrayList();
	private ArrayList latestEvents = new ArrayList();  
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		communicator = (FrontCommunicator)this.getActivity();
		
		mRoot = inflater.inflate(R.layout.fragment_home, container,
                false);
		linlaHeaderProgress = (LinearLayout) mRoot.findViewById(R.id.linlaHeaderProgress);
		list = (GridView) mRoot.findViewById(R.id.gridlist);
		list2 = (GridView) mRoot.findViewById(R.id.gridlist2);
		list3 = (GridView) mRoot.findViewById(R.id.gridlist3);
		//list4 = (GridView) mRoot.findViewById(R.id.gridlist4);
		list5 = (GridView) mRoot.findViewById(R.id.gridlist5);
		list6 = (GridView) mRoot.findViewById(R.id.gridlist6);
		list7 = (GridView) mRoot.findViewById(R.id.gridlist7);
		//list8 = (GridView) mRoot.findViewById(R.id.gridlist8);
		
		button1= (Button) mRoot.findViewById(R.id.button1);
		button2= (Button) mRoot.findViewById(R.id.button2);
		button3= (Button) mRoot.findViewById(R.id.button3);
		//button4= (Button) mRoot.findViewById(R.id.button4);
		button5= (Button) mRoot.findViewById(R.id.button5);
		button6= (Button) mRoot.findViewById(R.id.button6);
		button7= (Button) mRoot.findViewById(R.id.button7);
		//button8= (Button) mRoot.findViewById(R.id.button8);
		
		rLayout1 = (RelativeLayout) mRoot.findViewById(R.id.region1);
		rLayout2 = (RelativeLayout) mRoot.findViewById(R.id.region2);
		rLayout3 = (RelativeLayout) mRoot.findViewById(R.id.region3);
		//rLayout4 = (RelativeLayout) mRoot.findViewById(R.id.region4);
		rLayout5 = (RelativeLayout) mRoot.findViewById(R.id.region5);
		rLayout6 = (RelativeLayout) mRoot.findViewById(R.id.region6);
		rLayout7 = (RelativeLayout) mRoot.findViewById(R.id.region7);
		//rLayout8 = (RelativeLayout) mRoot.findViewById(R.id.region8);
		
		button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectForum();
            }
        });
		
		
		rLayout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectForum();
            }
        });
		rLayout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectBlog();
            }
        });
		button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectBlog();
            }
        });
		rLayout3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
		button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
		/*
		rLayout4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
		button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
        */
		rLayout5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectVolunteer();
            }
        });
		button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectVolunteer();
            }
        });
		rLayout6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectJob();
            }
        });
		button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectJob();
            }
        });
		rLayout7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectJetso();
            }
        });
		button7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectJetso();
            }
        });
		
		/*
		rLayout8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
		button8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	communicator.selectActivity();
            }
        });
	       */
		 
		linear = (LinearLayout) mRoot.findViewById(R.id.info);
		linear.setVisibility(View.GONE);
		
		if(featuredEvents.size()==0){
			task = new DownloadWebPageTask();
		    task.execute(new String[] { "https://www.happy-retired.com/activitywebservice" });
		}
		mAdapter = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, latestForums);
        mAdapter.notifyDataSetChanged();
        list.invalidateViews();
        list.setAdapter(mAdapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				//communicator.respond(position, mlist);
				communicator.selectForum(position, latestForums);
			}
		});
        
        /*
        list.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });
        */
        
		mAdapter2 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, featuredBlogs);
        mAdapter2.notifyDataSetChanged();
        list2.invalidateViews();
        list2.setAdapter(mAdapter2);
        
        list2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectBlog(position, featuredBlogs);
			}
		});
        /*
        list2.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });
        */
		
        mAdapter3 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, featuredEvents);
        mAdapter3.notifyDataSetChanged();
        list3.invalidateViews();
        list3.setAdapter(mAdapter3);
        
        list3.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectActivity(position, featuredEvents);
			}
		});
        
        /*
        mAdapter4 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, comingEvents);
        mAdapter4.notifyDataSetChanged();
        list4.invalidateViews();
        list4.setAdapter(mAdapter4);
        
        list4.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectActivity(position, comingEvents);
			}
		});
		*/
        /*
        list3.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });
        */
        
        mAdapter5 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, featuredVolunteers);
        mAdapter5.notifyDataSetChanged();
        list5.invalidateViews();
        list5.setAdapter(mAdapter5);
        
        list5.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectVolunteer(position, featuredVolunteers);
			}
		});
        
        
        mAdapter6 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, latestJobs);
        mAdapter6.notifyDataSetChanged();
        list6.invalidateViews();
        list6.setAdapter(mAdapter6);
        
        list6.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectJob(position, latestJobs);
			}
		});
        
        mAdapter7 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, latestJetsos);
        mAdapter7.notifyDataSetChanged();
        list7.invalidateViews();
        list7.setAdapter(mAdapter7);
        
        list7.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectJetso(position, latestJetsos);
			}
		});
        
        /*
        mAdapter8 = new FrontPageAdapter(this.getActivity(), android.R.layout.simple_list_item_1, latestEvents);
        mAdapter8.notifyDataSetChanged();
        list8.invalidateViews();
        list8.setAdapter(mAdapter8);
        
        list8.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				communicator.selectActivity(position, latestEvents);
			}
		});
		*/
        
		/*
		mAdapter = new BloggerListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mlist);
		list.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
        list.invalidateViews();
        */
		
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

        try {
        	HttpGet httpGet = new HttpGet(getActivity().getResources().getString(R.string.WEBSERVICE_COMMON)+"?action=getAppFirstPage");
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
	            Log.e(BloggerTabsFragment.class.toString(), "Failed to download file");
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
    
    protected void getCategory(JSONObject  jsnobject) throws Exception{
    	        
        JSONArray  jsonArray = jsnobject.getJSONArray("featuredEvent");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
    	        ActivityItem activityItem = new ActivityItem();
	        	//assignToItem(activityItem, i, jsonObject);
	        	activityItem.assignToItem(i, jsonObject);
	        	featuredEvents.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("comingEvent");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
    	        ActivityItem activityItem = new ActivityItem();
	        	//assignToItem(activityItem, i, jsonObject);
	        	activityItem.assignToItem(i, jsonObject);
	        	comingEvents.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("latestEvent");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
    	        ActivityItem activityItem = new ActivityItem();
	        	//assignToItem(activityItem, i, jsonObject);
	        	activityItem.assignToItem(i, jsonObject);
	        	latestEvents.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("latestForum");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
	        	ForumTopicItem activityItem = new ForumTopicItem();
	        	//assignToItem(activityItem, i, jsonObject);
	        	activityItem.assignToItem(i, jsonObject);
	        	latestForums.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("featuredBlog");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
	        	Blogger activityItem = new Blogger();
	        	activityItem.assignToItem(i, jsonObject);
	        	featuredBlogs.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("featuredVolunteer");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
	        	VolunteerItem activityItem = new VolunteerItem();
	        	activityItem.assignToItem(i, jsonObject);
	        	featuredVolunteers.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("latestJob");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
	        	JobItem activityItem = new JobItem();
	        	activityItem.assignToItem(jsonObject);
	        	latestJobs.add(activityItem);
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        jsonArray = jsnobject.getJSONArray("latestJetso");

        try{
        	for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
    	        
	        	JetsoItem activityItem = new JetsoItem();
	        	activityItem.assignToItem(i, jsonObject);
	        	latestJetsos.add(activityItem);
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
        	 JSONObject jsonObj;
            String readTwitterFeed = readActivityFeed();
            try {
              //jsonArray = new JSONArray(readTwitterFeed);
            	if(readTwitterFeed!=null || readTwitterFeed.equals("")){
	              jsonObj = new JSONObject(readTwitterFeed);
	              getCategory(jsonObj);
            	}
            } catch (Exception e) {
              e.printStackTrace();
            }
            
            
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = getActivity();       
        	super.onPostExecute(result);
        	
        	mAdapter7 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, latestJetsos);
            mAdapter7.notifyDataSetChanged();
            list7.invalidateViews();
            list7.setAdapter(mAdapter7);
              
        	//swipeLayout.setRefreshing(false);
            mAdapter = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, latestForums);
            mAdapter.notifyDataSetChanged();
            list.invalidateViews();
            list.setAdapter(mAdapter);
            
            mAdapter2 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, featuredBlogs);
            mAdapter2.notifyDataSetChanged();
            list2.invalidateViews();
            list2.setAdapter(mAdapter2);
            
            mAdapter3 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, featuredEvents);
            mAdapter3.notifyDataSetChanged();
            list3.invalidateViews();
            list3.setAdapter(mAdapter3);
            
            /*
            mAdapter4 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, comingEvents);
            mAdapter4.notifyDataSetChanged();
            list4.invalidateViews();
            list4.setAdapter(mAdapter4);
            */
            
            mAdapter5 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, featuredVolunteers);
            mAdapter5.notifyDataSetChanged();
            list5.invalidateViews();
            list5.setAdapter(mAdapter5);
            
            mAdapter6 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, latestJobs);
            mAdapter6.notifyDataSetChanged();
            list6.invalidateViews();
            list6.setAdapter(mAdapter6);
            
            /*
            mAdapter8 = new FrontPageAdapter(myContext, android.R.layout.simple_list_item_1, latestEvents);
            mAdapter8.notifyDataSetChanged();
            list8.invalidateViews();
            list8.setAdapter(mAdapter8);
            */
          
            
            linlaHeaderProgress.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
            //mdialog.dismiss();
        }
        
        
      }
}
