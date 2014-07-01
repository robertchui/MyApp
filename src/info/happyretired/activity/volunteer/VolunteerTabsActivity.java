package info.happyretired.activity.volunteer;

import info.happyretired.activity.CommonTabsActivity;
import info.happyretired.communicator.ActivityCommunicator;
import info.happyretired.R;




import java.util.ArrayList;

import com.example.android.common.view.SlidingTabLayout;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

public class VolunteerTabsActivity  extends CommonTabsActivity implements TabListener, ActivityCommunicator{

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment_activity);
        viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fm = this.getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fm, this));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(viewPager);
        mSlidingTabLayout.setCustomTabView(R.layout.tab, R.id.text);
        mSlidingTabLayout.setSelectedIndicatorColors( getResources().getColor(R.color.tab_color));
        
        actionBar.setTitle(getResources().getString(R.string.volunteer));
        
    }

    public void respond(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, VolunteerDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

    }
    

}

class MyAdapter extends FragmentPagerAdapter{
	private Context _context; 
	public int NUM_ITEM = 2;
	
	public MyAdapter(FragmentManager fragmentManager,  Context c) {
        super(fragmentManager);
        _context = c;
    }

	@Override
	public Fragment getItem(int i) {
		
		Fragment fragment = null;
		if(i==0){
			VolunteerTabsFragment fg = new VolunteerTabsFragment();
			fg.setTimeofService("1");
			fragment = (Fragment)fg;
			
		}
		if(i==1){
			VolunteerTabsFragment fg = new VolunteerTabsFragment();
			fg.setTimeofService("2");
			fragment = (Fragment)fg;
			
		}
		return fragment;
	}

	
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		String title = "";
		switch(position){
			case 0: title = _context.getResources().getString(R.string.volunteer_page_once);break;
			case 1: title = _context.getResources().getString(R.string.volunteer_page_regular);break;
		}
		return title;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_ITEM;
	}

	
	
}