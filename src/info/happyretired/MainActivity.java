package info.happyretired;

import info.happyretired.activity.blog.BlogTabsActivity;
import info.happyretired.activity.event.EventTabsActivity;
import info.happyretired.activity.forum.ForumTabsActivity;
import info.happyretired.activity.job.JobTabsActivity;
import info.happyretired.activity.volunteer.VolunteerTabsActivity;
import info.happyretired.adapter.BlogListAdapter;
import info.happyretired.adapter.NavDrawerListAdapter;
import info.happyretired.dao.PreferenceDAO;
import info.happyretired.db.MySQLiteHelper;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.model.Preference;
import info.happyretired.R;
import info.happyretired.service.NotificationService;
import info.happyretired.ult.CommonConstant;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;




import android.widget.TextView;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.google.android.vending.licensing.AESObfuscator;


public class MainActivity extends FragmentActivity   {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	 private FragmentTabHost mTabHost;
	 
	 private TextView mStatusText;
	 private Button mCheckLicenseButton;
	 private Handler mHandler;
	 private LicenseCheckerCallback mLicenseCheckerCallback;
	 private LicenseChecker mChecker;

	 private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhc9/Sip5nzxn/shIHFR8hDvFYWrd1bL0TjuFBQzqHPcz/LCBiwof6dnmHOu/dzV+OzuG6QOq0yT5wNji1knzYGMT3cIW4zGjN82t+ZJ7gPeX1quM0Aar2lLbHmrnXmWi/n9o8gosirOoSLbsv6ucDZTqO/Zd1kkbT1gtYwz92sMc4jijU6JBcfAyV6Icl5H6rhnbIk4vhn/N4S+bTbEczhyZcv1l8ds6zjlRSzb20TxGZHYvasMQFHCvkPyAZpVVWWPa+gH15NBxyHe6iA6FJ3vtX4RyM9goVzQNfTWz4rk15M+AkaVGBLkiNew+H87e28xeaq1Y3Ujs6tYNR5TSTwIDAQAB";

	    // Generate your own 20 random bytes, and put them here.
	    private static final byte[] SALT = new byte[] {
	        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
	        89
	    };
	    
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
			 finish();
			 return;
		}

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		mHandler = new Handler();
		//mStatusText = (TextView) findViewById(R.id.status_text);
        //mCheckLicenseButton = (Button) findViewById(R.id.check_license_button);
		/*
        mCheckLicenseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                doCheck();
            }
        });
        */
        
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        // Construct the LicenseChecker with a policy.
		
		// Try to use more data here. ANDROID_ID is a single point of attack.
        String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        
        mChecker = new LicenseChecker(
            this, new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(), deviceId)),
            BASE64_PUBLIC_KEY);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_cover);
    	
        DownloadWebPageTask task = new DownloadWebPageTask();
	    task.execute();
	}
	




	private void doCheck() {
		 //mCheckLicenseButton.setEnabled(false);
	        setProgressBarIndeterminateVisibility(true);
	        //mStatusText.setText(R.string.checking_license);
	        mChecker.checkAccess(mLicenseCheckerCallback);

	    }
	 
	 private void displayResult(final String result) {
	        mHandler.post(new Runnable() {
	            public void run() {
	                //mStatusText.setText(result);
	                setProgressBarIndeterminateVisibility(false);
	                //mCheckLicenseButton.setEnabled(true);
	            }
	        });
	    }
	
	 private void displayDialog(final boolean showRetry) {
	        mHandler.post(new Runnable() {
	            public void run() {
	                setProgressBarIndeterminateVisibility(false);
	                showDialog(showRetry ? 1 : 0);
	                //mCheckLicenseButton.setEnabled(true);
	            }
	        });
	    }    

	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	@Override
        protected void onPreExecute() {
    		
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	CommonConstant commonConstant = CommonConstant.getInstance();
    		commonConstant.init();
    		
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Intent k = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(k);
        }
        
        
      }
	
	public void onResume() {
	    super.onResume();
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    int minutes = prefs.getInt("interval", CommonConstant.NOTIFICATION_DEFAULT);
	    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
	    //Intent i = new Intent(this, NotificationService.class);
	    Intent i = new Intent(this, NotificationService.class);
	    PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
	    am.cancel(pi);
	    // by my own convention, minutes <= 0 means notifications are disabled
	    if (minutes > 0) {
	    	Calendar cal = Calendar.getInstance();
	    	am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), minutes*60*1000, pi);
	    	/*
	        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
	            SystemClock.elapsedRealtime() + minutes*60*1000,
	            minutes*60*1000, pi);
	            */
	    }
	}
	
	private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // Should allow user access.
            displayResult(getString(R.string.allow));
        }

        public void dontAllow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            displayResult(getString(R.string.dont_allow));
            // Should not allow access. In most cases, the app should assume
            // the user has access unless it encounters this. If it does,
            // the app should inform the user of their unlicensed ways
            // and then either shut down the app or limit the user to a
            // restricted set of features.
            // In this example, we show a dialog that takes the user to Market.
            // If the reason for the lack of license is that the service is
            // unavailable or there is another problem, we display a
            // retry button on the dialog and a different message.
            displayDialog(policyReason == Policy.RETRY);
        }

        public void applicationError(int errorCode) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // This is a polite way of saying the developer made a mistake
            // while setting up or calling the license checker library.
            // Please examine the error code and fix the error.
            String result = String.format(getString(R.string.application_error), errorCode);
            displayResult(result);
        }
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //mChecker.onDestroy();
    }
}
