package info.happyretired.service;

import info.happyretired.R;
import info.happyretired.activity.HomeActivity;
import info.happyretired.activity.MainActivity;
import info.happyretired.activity.blog.BlogDetailsActivity;
import info.happyretired.activity.blog.BlogTabsFragment;
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.activity.jetso.JetsoDetailsActivity;
import info.happyretired.activity.volunteer.VolunteerDetailsActivity;
import info.happyretired.dao.PreferenceDAO;
import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.model.JetsoItem;
import info.happyretired.model.Preference;
import info.happyretired.model.VolunteerItem;
import info.happyretired.ult.CommonConstant;

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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;

public class NotificationService extends Service {
    
    private WakeLock mWakeLock;
    private JSONArray jsonArray;
    
    /**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    /**
     * This is where we initialize. We call this when onStart/onStartCommand is
     * called by the system. We won't do anything with the intent here, and you
     * probably won't, either.
     */
    private void handleIntent(Intent intent) {
        // obtain the wake lock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        //mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Const.TAG);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Const.TAG");
        mWakeLock.acquire();
        
        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }
        
        // do the actual work, in a separate thread
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean isNotification = prefs.getBoolean(CommonConstant.NOTIFICATION, true);
	    
	    if(isNotification)
	    	new PollTask().execute();
    }
    
    public String readActivityFeed() {
	   	
    	StrictMode.ThreadPolicy policy = new StrictMode.
    	          ThreadPolicy.Builder().permitAll().build();
    	        StrictMode.setThreadPolicy(policy); 
    	        
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        
        Context context = getApplicationContext();
    	
    	PreferenceDAO dao = new PreferenceDAO(context);
     	Preference obj = dao.getPreference();
     	
        HttpGet httpGet = new HttpGet(this.getResources().getString(R.string.WEBSERVICE_COMMON)+"?action=getLatestMessage&message_id="+obj.getNofiticationID());
        
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
            Log.e(BlogTabsFragment.class.toString(), "Failed to download file");
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

    
    private class PollTask extends AsyncTask<Void, Void, Void> {
        /**
         * This is where YOU do YOUR work. There's nothing for me to write here
         * you have to fill this in. Make your HTTP request(s) or whatever it is
         * you have to do to get your updates in here, because this is run in a
         * separate thread
         */
        @Override
        protected Void doInBackground(Void... params) {
            // do stuff!
        	String myBlog = "http://android-er.blogspot.com/";
        	
        	
        	
        	 String readTwitterFeed = readActivityFeed();
             try {
            	 if(readTwitterFeed!=null && readTwitterFeed.length()>0){
            		 jsonArray = new JSONArray(readTwitterFeed);
            		 JSONObject jsonObject = jsonArray.getJSONObject(0);
            		 String notificationID = jsonObject.getString("id");
            		 String title = jsonObject.getString("title");
            		 String subject = jsonObject.getString("subject");
            		 String subTitle = jsonObject.getString("subTitle");
            		 String category = jsonObject.getString("category");
            		 String refNo = jsonObject.getString("ref");
            		 
            		 Context context = getApplicationContext();
            		 PreferenceDAO dao = new PreferenceDAO(context);
            	     Preference obj = dao.getPreference();
            	     obj.setNofiticationID(Integer.parseInt(notificationID));
            	     dao.update(obj);
            	     
            		 //Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
            	                 	     
            		 Intent myIntent = null;
            		 
            		 if(category.equals("BLOG")){
            			 
            			ArrayList in = new ArrayList();
            			Blogger item = new Blogger();
            			item.setRefNo(refNo);
            			in.add(item);
            			
            			myIntent = new Intent(getApplicationContext(), BlogDetailsActivity.class);
            			myIntent.putExtra("currentPage", 0);
            			myIntent.putParcelableArrayListExtra ("para", in);
            		 }
            		 else if(category.equals("ACTIVITY")){
            			 
             			ArrayList in = new ArrayList();
             			ActivityItem item = new ActivityItem();
             			item.setRefNo(refNo);
             			in.add(item);
             			
             			myIntent = new Intent(getApplicationContext(), EventDetailsActivity.class);
             			myIntent.putExtra("currentPage", 0);
             			myIntent.putParcelableArrayListExtra ("para", in);
             		 }
            		 else if(category.equals("VOLUNTEER")){
            			 
             			ArrayList in = new ArrayList();
             			VolunteerItem item = new VolunteerItem();
             			item.setRefNo(refNo);
             			in.add(item);
             			
             			myIntent = new Intent(getApplicationContext(), VolunteerDetailsActivity.class);
             			myIntent.putExtra("currentPage", 0);
             			myIntent.putParcelableArrayListExtra ("para", in);
             		 }
            		 else if(category.equals("JETSO")){
            			 
              			ArrayList in = new ArrayList();
              			JetsoItem item = new JetsoItem();
              			item.setRefNo(refNo);
              			in.add(item);
              			
              			myIntent = new Intent(getApplicationContext(), JetsoDetailsActivity.class);
              			myIntent.putExtra("currentPage", 0);
              			myIntent.putParcelableArrayListExtra ("para", in);
              		 }
            		 else{
            			 myIntent = new Intent(getApplicationContext(), HomeActivity.class);
            		 }
            		 
            		 
            		 sendNofitication(title,subTitle, subject, myIntent);
            	 }
             } catch (Exception e) {
               e.printStackTrace();
             }
			
             try{
            	 
             }
             catch(Exception e){
            	 e.printStackTrace();
             }
             
            return null;
        }
        
        /**
         * In here you should interpret whatever you fetched in doInBackground
         * and push any notifications you need to the status bar, using the
         * NotificationManager. I will not cover this here, go check the docs on
         * NotificationManager.
         *
         * What you HAVE to do is call stopSelf() after you've pushed your
         * notification(s). This will:
         * 1) Kill the service so it doesn't waste precious resources
         * 2) Call onDestroy() which will release the wake lock, so the device
         *    can go to sleep again and save precious battery.
         */
        @Override
        protected void onPostExecute(Void result) {
            // handle your data
        	
        			
            stopSelf();
        }
    }
    
    public void sendNofitication(String notificationTitle, String subTitle, String notificationText, Intent myIntent){
    	NotificationManager notificationManager =
   			 (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	   	Notification myNotification = new Notification(R.drawable.ic_launcher, subTitle, System.currentTimeMillis());
	   	
	   	Context context = getApplicationContext();    	
    	
    	PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, myIntent,
    			    Intent.FLAG_ACTIVITY_NEW_TASK);
    	myNotification.defaults |= Notification.DEFAULT_SOUND;
    	myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    	myNotification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
    	
    	int MY_NOTIFICATION_ID = 123456;
    	notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    	
    }
    /**
     * This is deprecated, but you have to implement it if you're planning on
     * supporting devices with an API level lower than 5 (Android 2.0).
     */
    @Override
    public void onStart(Intent intent, int startId) {
        //handleIntent(intent);
    	// do the actual work, in a separate thread
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean isNotification = prefs.getBoolean(CommonConstant.NOTIFICATION, true);
	    
	    if(isNotification)
	    	new PollTask().execute();
    }
    
    /**
     * This is called on 2.0+ (API level 5 or higher). Returning
     * START_NOT_STICKY tells the system to not restart the service if it is
     * killed because of poor resource (memory/cpu) conditions.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //handleIntent(intent);
    	// do the actual work, in a separate thread
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean isNotification = prefs.getBoolean(CommonConstant.NOTIFICATION, true);
	    
	    if(isNotification)
	    	new PollTask().execute();
        return START_NOT_STICKY;
    }
    
    /**
     * In onDestroy() we release our wake lock. This ensures that whenever the
     * Service stops (killed for resources, stopSelf() called, etc.), the wake
     * lock will be released.
     */
    public void onDestroy() {
        super.onDestroy();
        //mWakeLock.release();
    }
}