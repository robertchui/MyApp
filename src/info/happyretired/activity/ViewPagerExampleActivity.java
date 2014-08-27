package info.happyretired.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ortiz.touch.ExtendedViewPager;
import com.ortiz.touch.TouchImageView;

import info.happyretired.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ViewPagerExampleActivity extends Activity {
	
	/**
	 * Step 1: Download and set up v4 support library: http://developer.android.com/tools/support-library/setup.html
	 * Step 2: Create ExtendedViewPager wrapper which calls TouchImageView.canScrollHorizontallyFroyo
	 * Step 3: ExtendedViewPager is a custom view and must be referred to by its full package name in XML
	 * Step 4: Write TouchImageAdapter, located below
	 * Step 5. The ViewPager in the XML should be ExtendedViewPager
	 */

	ExtendedViewPager mViewPager;
	String url ;
	Context c;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_viewpager_example);
        mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        c = this.getApplicationContext();
        try{
        	
        	//url = new URL(getIntent().getExtras().getString("url"));
        	url = getIntent().getExtras().getString("url");
        	BitmapWorkerTask task = new BitmapWorkerTask();
		    task.execute();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
	
	class BitmapWorkerTask extends AsyncTask<Integer, Void, String> {
	    
	    private int data = 0;


	    // Decode image in background.
	    @Override
	    protected String doInBackground(Integer... params) {
	    	return "";
	    }

	    // Once complete, see if ImageView is still around and set bitmap.
	    @Override
	    protected void onPostExecute(String s) {

	    	try{
		    	//Bitmap bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
		    	//URL url = new URL(src);
	    		/*
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap bitmap = BitmapFactory.decodeStream(input);
		        */
	    		
	    		 DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
	    					//.showImageForEmptyUri(R.drawable.ic_launcher) 
	    					.build();
	    		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c.getApplicationContext())	
				.defaultDisplayImageOptions(defaultOptions)
				.build();
	    		ImageLoader.getInstance().init(config);
	    		//url = "https://www.happy-retired.com/images/photoupload/files/20140725175942/pizzabox.png";
	    		//url = "https://www.happy-retired.com/media/kunena/attachments/82/JHM_01_c_20140725_L.jpg";
	    		Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
		        
		        mViewPager.setAdapter(new TouchImageAdapter(bitmap));
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	}

    static class TouchImageAdapter extends PagerAdapter {

        private static int[] images = { R.drawable.nature_1};
        Bitmap bmp;
        
        public TouchImageAdapter(Bitmap bmp){
        	this.bmp = bmp;
        }
        @Override
        public int getCount() {
        	//return images.length;
        	return 1;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
            //img.setImageResource(images[position]);
            img.setImageBitmap(bmp);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
