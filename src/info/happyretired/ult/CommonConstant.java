package info.happyretired.ult;

import info.happyretired.activity.forum.ForumCategoryTabsFragment;
import info.happyretired.adapter.ForumCategoryListAdapter;
import info.happyretired.model.BlogPostItem;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.R;

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

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

public class CommonConstant {
	
	private static CommonConstant instance = null;
	
	private ArrayList <ForumCategoryItem> forumCategoryList;
	private ArrayList <ForumCategoryItem> blogCategoryList;
	
	//private JSONArray jsonArray;
	
	public final String forumCategoryUrl = "https://www.happy-retired.com/forumwebservice?action=getCategory";
	public final String blogCategoryUrl = "https://www.happy-retired.com/blogwebservice?action=getCategory";
	
	
	 private CommonConstant(){ }
	   
	   /* Static 'instance' method */
	   public static CommonConstant getInstance( ) {
		   if(instance == null) {
		         instance = new CommonConstant();
		         //instance.init();
		   }
		      return instance;
	   }
	   
	public ArrayList<ForumCategoryItem> getBlogCategoryList() {
		
		if(blogCategoryList==null)
			init();
		
		return blogCategoryList;
	}   
	   
	public ArrayList<ForumCategoryItem> getForumCategoryList() {
		
		if(forumCategoryList==null)
			init();
		
		return forumCategoryList;
	}


	public void init(){
		JSONArray jsonArray = new JSONArray();
		   if(forumCategoryList==null){ 
			   String readTwitterFeed = readActivityFeed(forumCategoryUrl);
	            try {
	              jsonArray = new JSONArray(readTwitterFeed);
	            } catch (Exception e) {
	              e.printStackTrace();
	            }
	            	
	            forumCategoryList = getForumCategoryItem(jsonArray);
		   }
		   
		   if(blogCategoryList==null){ 
			   String readTwitterFeed = readActivityFeed(blogCategoryUrl);
	            try {
	              jsonArray = new JSONArray(readTwitterFeed);
	            } catch (Exception e) {
	              e.printStackTrace();
	            }
	            	
	            blogCategoryList = getForumCategoryItem(jsonArray);
		   }
	   }
	   
	   protected ArrayList getForumCategoryItem(JSONArray jsonArray){
		   ArrayList list = new ArrayList();    
	        int size = jsonArray.length();
	        try{
	        	for (int i = 0; i < jsonArray.length(); i++) {
		        	JSONObject jsonObject = jsonArray.getJSONObject(i);
		        	
		        		ForumCategoryItem activityItem = new ForumCategoryItem();
		        		activityItem.setId(jsonObject.getString("id"));
		            	activityItem.setName(jsonObject.getString("name"));
		            	list.add(activityItem);
		
		        }
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        return list;
	    }
	   
	   private String readActivityFeed(String url) {
   	   	
	    	StrictMode.ThreadPolicy policy = new StrictMode.
	    	          ThreadPolicy.Builder().permitAll().build();
	    	        StrictMode.setThreadPolicy(policy); 
	    	        
	        StringBuilder builder = new StringBuilder();
	        HttpClient client = new DefaultHttpClient();

	        HttpGet httpGet = new HttpGet(url);
	        
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
	            Log.e(ForumCategoryTabsFragment.class.toString(), "Failed to download file");
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

	        }
	    	
	    	
	        @Override
	        protected String doInBackground(String... urls) {
	        	
	        	int i = urls.length;
	        	JSONArray jsonArray = new JSONArray();
	        	
	        	  String readTwitterFeed = readActivityFeed(urls[1]);
		            try {
		              jsonArray = new JSONArray(readTwitterFeed);
		            } catch (Exception e) {
		              e.printStackTrace();
		            }
		            
		            if(urls[0].equals("forumCategory"))
		            	
		            getForumCategoryItem(jsonArray);
	            
	            return "";
	        }

	        @Override
	        protected void onPostExecute(String result) {
	        
	        }
	        
	        
	      }
	   
}
