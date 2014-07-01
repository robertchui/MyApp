package info.happyretired.activity.blog;

import info.happyretired.communicator.BlogCommunicator;
import info.happyretired.model.Blogger;
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
import android.support.v7.widget.ShareActionProvider;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;


public class BloggerPostListActivity extends FragmentActivity implements BlogCommunicator{

	ActionBar actionBar = null;
	ViewPager viewPager = null;
	ArrayList  inputArray= null;
	int currentPage;
	ArrayList <Fragment>fragments = new ArrayList();
	private ShareActionProvider mShareActionProvider;

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
		//getMenuInflater().inflate(R.menu.share_menu, menu);

		return true;

	}
	
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}
	
	 public void goToPage(int page){
	    	viewPager.setCurrentItem(page+2);
	    }
	    
	 public void respond(int currentPage, ArrayList in){
    	
    }
    
	 public void respondReadArticle(int currentPage, ArrayList in){
    	
    	Intent intent = new Intent(this, BlogDetailsActivity.class);
    	intent.putExtra("currentPage", currentPage);
    	intent.putParcelableArrayListExtra ("para", in);
    	startActivity(intent);

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
        //actionBar.setDisplayHomeAsUpEnabled(true);
        
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
            	
            }
        };
        viewPager.setOnPageChangeListener(pageChangeListener);
        
        if(fragments.size()==0){
        fragments = new ArrayList(inputArray.size());
	        for (int i = 0; i < inputArray.size(); i++)
	        {
	        	BloggerPostListFragment fg = new BloggerPostListFragment();
				fg.setTopicItem((Blogger)inputArray.get(i));
	            fragments.add(fg);
	        }
        }
        
        
     
        /*
		ActivityDetailsFragment f3 = (ActivityDetailsFragment)this.getSupportFragmentManager().findFragmentById(R.id.fragment3);
		if(f3!=null)
			f3.changeData(inputArray);
			*/
		
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