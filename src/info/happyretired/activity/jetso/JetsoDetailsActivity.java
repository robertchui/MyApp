package info.happyretired.activity.jetso;

import info.happyretired.model.ActivityItem;
import info.happyretired.model.Blogger;
import info.happyretired.model.JetsoItem;
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

public class JetsoDetailsActivity extends FragmentActivity{

	JetsoItem activityItem;
	ActionBar actionBar = null;
	ViewPager viewPager = null;
	ArrayList  inputArray= null;
	int currentPage;
	ArrayList <Fragment>fragments = new ArrayList();
	private ShareActionProvider mShareActionProvider;

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_menu, menu);
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_cart);
	    
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    
	    updateShareButton();

		return true;

	}
	
	public void updateShareButton(){
		Intent myIntent = new Intent();
        myIntent.setAction(Intent.ACTION_SEND);
        myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
        myIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(myIntent);
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
		setContentView(R.layout.layout_activity_details_activity);
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
        viewPager.setAdapter(new MyAdapter(fm, this, inputArray.size()));
        viewPager.setCurrentItem(currentPage);
       
        
        OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
            	activityItem = (JetsoItem)inputArray.get(position);
            	Intent myIntent = new Intent();
    	        myIntent.setAction(Intent.ACTION_SEND);
    	        //myIntent.putExtra(Intent.EXTRA_SUBJECT, activityItem.getTitle()+" | "+activityItem.getCompany_name());
    	        //myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
    	        myIntent.putExtra(Intent.EXTRA_TEXT, activityItem.getShareUrl());
    	        myIntent.setType("text/plain");
    	        mShareActionProvider.setShareIntent(myIntent);
            	showPageNumber(position);
            }
        };
        
        activityItem = (JetsoItem)inputArray.get(currentPage);
        
        viewPager.setOnPageChangeListener(pageChangeListener);
        
        if(fragments.size()==0){
        fragments = new ArrayList(inputArray.size());
	        for (int i = 0; i < inputArray.size(); i++)
	        {
	        	JetsoDetailsFragment fg = new JetsoDetailsFragment();
				fg.setPageDetails(i, inputArray.size());
				fg.changeData((JetsoItem)inputArray.get(i));
	            fragments.add(fg);
	        }
        }
        
        showPageNumber(currentPage);
     
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
	
	public void showPageNumber(int position){
		LayoutInflater inflater = getLayoutInflater();
    	View layout =  inflater.inflate(R.layout.layout_popup_pageno, (ViewGroup) findViewById(R.id.toast_layout_root));
		TextView text = (TextView) layout.findViewById(R.id.currentPage);
		text.setText((position+1)+" / " + fragments.size());

		final Toast toast = new Toast(getApplicationContext());
		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
		Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
           @Override
           public void run() {
        	   toast.cancel(); 
           }
        }, 900);
        
	}
	
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
			
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_ITEM;
		}
		
		 
		
	}

}