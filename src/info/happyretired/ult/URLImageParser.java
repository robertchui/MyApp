package info.happyretired.ult;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class URLImageParser implements ImageGetter {
    Context c;
    View container;
    ArrayAdapter adapter;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(ArrayAdapter adapter, View t, Context c) {
        this.c = c;
        this.container = t;
        this.adapter = adapter;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask = 
            new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);
       
        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
        
        //Drawable drwa = asyncTask.fetchDrawable(source);
        //return drwa;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {

        	DisplayMetrics metrics = c.getResources().getDisplayMetrics();
            int height = metrics.heightPixels;
            int width = (int)(metrics.widthPixels*0.93);
            float scalingFactor =  width / (float)result.getIntrinsicWidth();
            
            //drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0+ drawable.getIntrinsicHeight()); 
            urlDrawable.setBounds(0, 0, 0 + width, (int) (result.getIntrinsicHeight()*scalingFactor));
            
            // set the correct bound according to the result from HTTP call
            /*
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 
                    + result.getIntrinsicHeight());
                    */

            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            //URLImageParser.this.container.invalidate();
            
            
            

            // Pre ICS
            
            TextView t = (TextView) URLImageParser.this.container; 
            t.setText(t.getText());
            container.postInvalidate();
            container.invalidate();
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");
                
                
                DisplayMetrics metrics = c.getResources().getDisplayMetrics();
                int height = metrics.heightPixels;
                int width = (int)(metrics.widthPixels*0.93);
                //int width = (int)(metrics.widthPixels)-10;
                float scalingFactor =  width / (float)drawable.getIntrinsicWidth();
                
                //drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0+ drawable.getIntrinsicHeight()); 
                drawable.setBounds(0, 0, 0 + width, (int) (drawable.getIntrinsicHeight()*scalingFactor));
                return drawable;
            } catch (Exception e) {
                return null;
            } 
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            return response.getEntity().getContent();
        }
    }
}