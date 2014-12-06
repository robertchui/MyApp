package info.happyretired.activity.userprofile;

import info.happyretired.activity.LoginActivity;
import info.happyretired.activity.UserSettingActivity;
import info.happyretired.activity.ViewPagerExampleActivity;
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
import info.happyretired.activity.userprofile.ProfileActivity;
import info.happyretired.activity.volunteer.VolunteerDetailsActivity;
import info.happyretired.activity.volunteer.VolunteerTabsActivity;
import info.happyretired.adapter.NavDrawerListAdapter;
import info.happyretired.communicator.FrontCommunicator;
import info.happyretired.communicator.ProfileCommunicator;
import info.happyretired.model.NavDrawerItem;
import info.happyretired.HomeActivity;
import info.happyretired.R;
import info.happyretired.service.MyService;
import info.happyretired.ult.CommonConstant;
import info.happyretired.ult.MyGoogleAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.example.androidhive.library.UserFunctions;
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Matrix;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity implements ProfileCommunicator   {
	
	private static final String SCREEN_LABEL = "Home Screen";
	
	private ShareActionProvider mShareActionProvider;
	
	private UserFunctions userFunction = new UserFunctions();
	Dialog settingsDialog;
	 
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		
       
		
		return super.onCreateView(name, context, attrs);
	}
	
	public void respond(int currentItem, ArrayList list){
		
	}
	
	public void goToPage(int position){
		switch (position) {
			case 0:
				Intent k = new Intent(ProfileActivity.this, PersonalDetailsActivity.class);
				startActivity(k);
				break;
				/*
			case 1:
				Toast.makeText((Activity) this.getApplicationContext(), "我們正努力建立此功能", Toast.LENGTH_LONG).show();
				break;
				*/
			case 1:
				
				final int orientation = getResources().getConfiguration().orientation; 
            	if (orientation == 1){ 
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
				} 
            	
				settingsDialog.show();
				
				settingsDialog.setOnCancelListener(new OnCancelListener() { 
                    @Override 
                    public void onCancel(DialogInterface dialog) {
                    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } 
				}); 
				//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
				break;
			case 2:
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("登出");
				alert.setMessage("立即登出?");
				alert.setPositiveButton("登出", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					userFunction.logoutUser(getApplicationContext());
					Intent k = new Intent(ProfileActivity.this, HomeActivity.class);
					startActivity(k);
					finish();
				  }
				});

				alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				break;
			default:
				break;
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//init constant
		setContentView(R.layout.activity_main);
		
		getActionBar().setTitle(getResources().getString(R.string.membership));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		displayView(0);
		
		settingsDialog = new Dialog(this);
		settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		View mRoot = getLayoutInflater().inflate(R.layout.membership_card, null);
		TextView ref = (TextView) mRoot.findViewById(R.id.member_id);
		ImageView imageView = (ImageView) mRoot.findViewById(R.id.card);
		
		UserFunctions userFunction = new UserFunctions();
		HashMap user = userFunction.getUserDetails(getApplicationContext().getApplicationContext());
		 
		int yournumber = Integer.parseInt((String)user.get("uid"));
		
		ref.setText("會員編號 : " + String.format("%07d", yournumber));
		
		/*
		Matrix matrix=new Matrix();
		imageView.setRotation(90);
		ref.setRotation(90);
		imageView.setImageResource(R.drawable.membership_card);
		*/
		
		settingsDialog.setContentView(mRoot);
		settingsDialog.setCancelable(true); 
		
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
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		Intent k = null;
		
		
		switch (position) {
		case 0:
			fragment = new ProfileFragment();
			break;
		
		default:
			break;
		}
		
		

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			 
		} else {
			// error in creating fragment
			//Log.e("MainActivity", "Error in creating fragment");
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
}
