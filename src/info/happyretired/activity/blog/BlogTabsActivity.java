package info.happyretired.activity.blog;

import info.happyretired.activity.CommonTabsActivity;
import info.happyretired.activity.event.EventDetailsActivity;
import info.happyretired.communicator.BlogCommunicator;
import info.happyretired.model.BlogPostItem;
import info.happyretired.model.ForumCategoryItem;
import info.happyretired.R;
import info.happyretired.R.color;
import info.happyretired.R.id;
import info.happyretired.R.layout;
import info.happyretired.R.menu;
import info.happyretired.R.string;


import info.happyretired.ult.CommonConstant;

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

public class BlogTabsActivity  extends CommonTabsActivity implements TabListener, BlogCommunicator{

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
        actionBar.setTitle(getResources().getString(R.string.blog));
      
        
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
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//
		if(viewPager.getCurrentItem()==0)
			super.onBackPressed();
		else
			viewPager.setCurrentItem(0);
	}


    
    public void goToPage(int page){
    	viewPager.setCurrentItem(page+2);
    }
    
    public void respond(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, BloggerPostListActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

    }
    
public void respondReadArticle(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, BlogDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

    }

}

class MyAdapter extends FragmentPagerAdapter{
	private Context _context; 
	public int NUM_ITEM = 3;
	
	public MyAdapter(FragmentManager fragmentManager,  Context c) {
        super(fragmentManager);
        _context = c;
    }

	@Override
	public Fragment getItem(int i) {
		
		Fragment fragment = null;
		
		if(i==0){
			BloggerTabsFragment fg = new BloggerTabsFragment();
			fg.setFeatured("Y");
			fragment = (Fragment)fg;		
		}
		else if(i==1){
			BloggerTabsFragment fg = new BloggerTabsFragment();
			fg.setFeatured("N");
			fragment = (Fragment)fg;		
		}
		else if(i==2){
			
			BlogTabsFragment fg = new BlogTabsFragment();
			fg.setCategoryId("");
			fragment = (Fragment)fg;
			
			//fragment = (Fragment)(new FragmentA());
		}
		
		
		return fragment;
	}

	
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		String title = "";
		
		
		
		if(position==0){
			title = _context.getResources().getString(R.string.blogContentTitle1);
		}
		else if(position==1){
			title = _context.getResources().getString(R.string.blogContentTitle2);
		}
		else if(position==2){
			title = _context.getResources().getString(R.string.blogContentTitle3);
		}

		
		return title;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_ITEM;
	}

	
	
}