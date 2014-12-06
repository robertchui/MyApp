package info.happyretired.activity.forum;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.ForumTopicItem;
import info.happyretired.model.VolunteerItem;
import info.happyretired.R;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.menu;

import java.util.ArrayList;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class ForumDetailsActivity extends FragmentActivity{

	ActionBar actionBar = null;
	ViewPager viewPager = null;
	ArrayList  inputArray= null;
	int currentPage;
	ArrayList <Fragment>fragments = new ArrayList();
	private ShareActionProvider mShareActionProvider;
	ForumTopicItem activityItem;
	MyAdapter myAdapter;
	
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_menu, menu);
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_cart);
	    
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    
	    Intent myIntent = new Intent();
	        myIntent.setAction(Intent.ACTION_SEND);
	        //myIntent.putExtra(Intent.EXTRA_SUBJECT, activityItem.getSubject());
	        myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
	        myIntent.setType("text/plain");
	        mShareActionProvider.setShareIntent(myIntent);

		return true;

	}
	
	// Call to update the share intent
		private void setShareIntent(Intent shareIntent) {
		    if (mShareActionProvider != null) {
		        mShareActionProvider.setShareIntent(shareIntent);
		    }	
		}
	
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}
	
	

	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		// TODO Auto-generated method stub
		
		return super.onCreateView(parent, name, context, attrs);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forum_details);
		actionBar =  getActionBar();
		//ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.tab_color));
		//actionBar.setBackgroundDrawable(colorDrawable); 
        actionBar.setDisplayHomeAsUpEnabled(true);
        
		Intent intent = getIntent();
		//String refNo = intent.getStringExtra("refNo");
		inputArray = intent.getParcelableArrayListExtra ("para");
		currentPage = intent.getIntExtra("currentPage", 0);
		viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fm = this.getSupportFragmentManager();
        myAdapter = new MyAdapter(fm, this, inputArray.size());
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(currentPage);
       
        
        OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
            	activityItem = (ForumTopicItem)inputArray.get(position);
            	Intent myIntent = new Intent();
    	        myIntent.setAction(Intent.ACTION_SEND);
    	        //myIntent.putExtra(Intent.EXTRA_SUBJECT, activityItem.getSubject());
    	        //myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
    	        myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
    	        myIntent.setType("text/plain");
    	        mShareActionProvider.setShareIntent(myIntent);
            }
        };
        
        activityItem = (ForumTopicItem)inputArray.get(currentPage);
        
        viewPager.setOnPageChangeListener(pageChangeListener);
        
        if(fragments.size()==0){
        fragments = new ArrayList(inputArray.size());
	        for (int i = 0; i < inputArray.size(); i++)
	        {
	        	ForumDetailsFragment fg = new ForumDetailsFragment();
				fg.setTopicItem((ForumTopicItem)inputArray.get(i));
	            fragments.add(fg);
	        }
        }
        
        
     
        /*
		ActivityDetailsFragment f3 = (ActivityDetailsFragment)this.getSupportFragmentManager().findFragmentById(R.id.fragment3);
		if(f3!=null)
			f3.changeData(inputArray);
			*/
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		    	 
		    	Intent intent = getIntent();
		     	intent.putExtra("currentPage", viewPager.getCurrentItem());
		    	finish();
		    	startActivity(intent);
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         //Do nothing?
		     }
		  }
		}//onActivityResult
	
	class MyAdapter extends FragmentPagerAdapter{
		private Context _context; 
		public int NUM_ITEM;
		
		public MyAdapter(FragmentManager fragmentManager,  Context c, int total) {
	        super(fragmentManager);
	        _context = c;
	        NUM_ITEM = total;
	    }
		@Override
		public Fragment getItem(int position) {
			
			/*
			currentPage = position-1;
			if(currentPage<0)
				currentPage = 0;
				*/
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_ITEM;
		}
		
		 
		
	}

}