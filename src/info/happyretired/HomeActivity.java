package info.happyretired;

import info.happyretired.activity.UserSettingActivity;
import info.happyretired.activity.blog.BlogDetailsActivity;
import info.happyretired.activity.blog.BlogTabsActivity;
import info.happyretired.activity.blog.BloggerTabsFragment;
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.activity.event.EventTabsActivity;
import info.happyretired.activity.forum.ForumDetailsActivity;
import info.happyretired.activity.forum.ForumTabsActivity;
import info.happyretired.activity.jetso.JetsoDetailsActivity;
import info.happyretired.activity.jetso.JetsoTabsActivity;
import info.happyretired.activity.job.JobDetailsActivity;
import info.happyretired.activity.job.JobTabsActivity;
import info.happyretired.activity.volunteer.VolunteerDetailsActivity;
import info.happyretired.activity.volunteer.VolunteerTabsActivity;
import info.happyretired.adapter.NavDrawerListAdapter;
import info.happyretired.communicator.FrontCommunicator;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.R;
import info.happyretired.service.MyService;
import info.happyretired.ult.CommonConstant;
import info.happyretired.ult.MyGoogleAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.analytics.HitBuilders;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class HomeActivity extends FragmentActivity implements FrontCommunicator   {
	
	private static final String SCREEN_LABEL = "Home Screen";
	
	private ShareActionProvider mShareActionProvider;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private MenuItem mItem = null;
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
	 
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//MyGoogleAnalytics.getInstance().getGaTracker().set(Fields.SCREEN_NAME, SCREEN_LABEL);
		
		//init constant
		setContentView(R.layout.activity_main);
		
		CommonConstant commonConstant = CommonConstant.getInstance();
		commonConstant.init();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				//.showImageForEmptyUri(R.drawable.ic_launcher) 
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())	
				.defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		
		/*
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		*/
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],"{fa-home}", "#f63c2b"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],"{fa-shopping-cart}", "#ea55a2"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],"{fa-comments}", "#3f8be1"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],"{fa-thumbs-up}", "#7d1b7e"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],"{fa-camera}", "#a1c935"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],"{fa-heart}", "#ffa500"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],"{fa-suitcase}", "#7bc9ff"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7],"{fa-facebook}", "#4965a0"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],"{fa-link}", "#b22222"));
		
		
		
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(5, -1)));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				//getActionBar().setTitle("");
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		//if (savedInstanceState == null) {
			// on first time display view for first nav item
		displayView(0);
		//}
		
	}
	
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

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.homepage_menu, menu);
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_cart);
	    mItem = item;
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    
	    Intent myIntent = new Intent();
	    myIntent.setAction(Intent.ACTION_SEND);
	    //myIntent.putExtra(Intent.EXTRA_SUBJECT, activityItem.getTitle()+" | "+activityItem.getCompanyName());
	    myIntent.putExtra(Intent.EXTRA_TEXT,   this.getApplication().getResources().getString(R.string.happyretired_app) +" | "+this.getApplication().getResources().getString(R.string.happyretired_app_url));
	    myIntent.setType("text/plain");
	    mShareActionProvider.setShareIntent(myIntent);

	  
		return true;

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //do your work
        	
        	if(!mDrawerLayout.isDrawerOpen(mDrawerList))
        		mDrawerLayout.openDrawer(Gravity.LEFT);
        	else
        		mDrawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        }
        return super.onKeyDown(keyCode, event); 
    } 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
			case R.id.action_settings:
			    Intent intent = new Intent(this, UserSettingActivity.class);
			    startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		Intent k = null;
		
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			/*
			BloggerTabsFragment fg = new BloggerTabsFragment();
			fg.setFeatured("Y");
			fragment = (Fragment)fg;
			*/
			
			break;
		case 1:
			k = new Intent(HomeActivity.this, JetsoTabsActivity.class);
			startActivity(k);
			break;
		case 2:
			k = new Intent(HomeActivity.this, ForumTabsActivity.class);
			startActivity(k);
			break;	
		case 3:
			k = new Intent(HomeActivity.this, BlogTabsActivity.class);
			startActivity(k);
			break;
		case 4:
			k = new Intent(HomeActivity.this, EventTabsActivity.class);
			startActivity(k);
			break;
		case 5:
			k = new Intent(HomeActivity.this, VolunteerTabsActivity.class);
			startActivity(k);
		    break;
		case 6:
			k = new Intent(HomeActivity.this, JobTabsActivity.class);
			startActivity(k);
			break;
		
		case 7:
			k = new Intent(Intent.ACTION_VIEW);
			String url = "https://www.facebook.com/happyretiredcom";
			k.setData(Uri.parse(url));
			startActivity(k);
			break;
		
		case 8:
			k = new Intent(Intent.ACTION_VIEW);
			String url2 = "https://www.happy-retired.com";
			k.setData(Uri.parse(url2));
			startActivity(k);
			break;
		

			
		
			
			
		default:
			break;
		}
		
		

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			
	 		mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			 
		} else {
			// error in creating fragment
			//Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void onBackPressed()
	{	   
	    //moveTaskToBack(true); // exist app
	    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
	}
	
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		//getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	public void selectForum(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, ForumDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

    }
	public void selectForum(){
		Intent k = new Intent(HomeActivity.this, ForumTabsActivity.class);
		startActivity(k);
    }
	public void selectBlog(){
		Intent k = new Intent(HomeActivity.this, BlogTabsActivity.class);
		startActivity(k);
	}
	public void selectActivity(){
		Intent k = new Intent(HomeActivity.this, EventTabsActivity.class);
		startActivity(k);
	}
	public void selectVolunteer(){
		Intent k = new Intent(HomeActivity.this, VolunteerTabsActivity.class);
		startActivity(k);
	}
	public void selectJob(){
		Intent k = new Intent(HomeActivity.this, JobTabsActivity.class);
		startActivity(k);
	}
	public void selectJetso(){
		Intent k = new Intent(HomeActivity.this, JetsoTabsActivity.class);
		startActivity(k);
	}

	public void selectJob(int currentPage, ArrayList in){
		Intent intent = new Intent(this, JobDetailsActivity.class);
		intent.putExtra("currentPage", currentPage);
		intent.putParcelableArrayListExtra ("para", in);
		startActivity(intent);
	}
	
	public void selectBlog(int currentPage, ArrayList in){
		Intent intent = new Intent(this, BlogDetailsActivity.class);
		intent.putExtra("currentPage", currentPage);
		intent.putParcelableArrayListExtra ("para", in);
		startActivity(intent);
	}
	
	
	public void selectActivity(int currentPage, ArrayList in){
		Intent intent = new Intent(this, EventDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);
	}
	
	public void selectVolunteer(int currentPage, ArrayList in){
		Intent intent = new Intent(this, VolunteerDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);
	}
	
	public void selectJetso(int currentPage, ArrayList in){
		Intent intent = new Intent(this, JetsoDetailsActivity.class);
		intent.putExtra("currentPage", currentPage);
		intent.putParcelableArrayListExtra ("para", in);
		startActivity(intent);
	}
}
