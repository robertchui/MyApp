package info.happyretired.activity;

import info.happyretired.activity.blog.BlogTabsActivity;
import info.happyretired.activity.event.EventTabsActivity;
import info.happyretired.activity.forum.ForumTabsActivity;
import info.happyretired.activity.job.JobTabsActivity;
import info.happyretired.activity.volunteer.VolunteerTabsActivity;
import info.happyretired.adapter.BlogListAdapter;
import info.happyretired.adapter.NavDrawerListAdapter;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.R;
import info.happyretired.ult.CommonConstant;

import java.util.ArrayList;

import org.json.JSONArray;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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


public class CommonActivity extends FragmentActivity   {

	
	
}
