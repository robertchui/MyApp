package info.happyretired.activity.jetso;

import info.happyretired.activity.CommonTabsActivity;
import info.happyretired.activity.forum.ForumTabsFragment;
import info.happyretired.communicator.ActivityCommunicator;
import info.happyretired.communicator.JetsoCommunicator;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.ult.CommonConstant;
import info.happyretired.R;
import info.happyretired.R.color;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.menu;
import info.happyretired.R.string;






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
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

public class JetsoTabsActivity  extends CommonTabsActivity implements TabListener, JetsoCommunicator{

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
        setContentView(R.layout.tab_fragment_activity);
        viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fm = this.getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fm, this));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(viewPager);
        mSlidingTabLayout.setCustomTabView(R.layout.tab, R.id.text);
        mSlidingTabLayout.setSelectedIndicatorColors( getResources().getColor(R.color.tab_color));
        actionBar.setTitle(getResources().getString(R.string.jetso));
      
        
    }

    public void respond(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, JetsoDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

    }
    
    

}

class MyAdapter extends FragmentPagerAdapter{
	private Context _context; 
	public int NUM_ITEM = 1;
	
	public MyAdapter(FragmentManager fragmentManager,  Context c) {
        super(fragmentManager);
        _context = c;
    }

	@Override
	public Fragment getItem(int i) {
		
		Fragment fragment = null;
		/*
		if(i==0){
			
			JetsoTabsFragment fg = new JetsoTabsFragment();
			fragment = (Fragment)fg;
			
			//fragment = (Fragment)(new FragmentA());
			
		}
		*/		
		
			CommonConstant commonConstant = CommonConstant.getInstance();
			ArrayList<ForumCategoryItem> list = commonConstant.getJetsoCategoryList();
		
			ForumCategoryItem item = list.get(i);
			
			JetsoTabsFragment fg = new JetsoTabsFragment();
			fg.setCategoryId(item.getId());
			fragment = (Fragment)fg;
			
			//fragment = (Fragment)(new FragmentA());
		
		return fragment;
	}

	
	
	
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		String title = "";
		CommonConstant commonConstant = CommonConstant.getInstance();
		ArrayList<ForumCategoryItem> list = commonConstant.getJetsoCategoryList();
		ForumCategoryItem item = null;
		
		
		
			item = list.get(position);
			title = item.getName();
		
		return title;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		CommonConstant commonConstant = CommonConstant.getInstance();
		return commonConstant.getJetsoCategoryList().size();
	}

	
	
}