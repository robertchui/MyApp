package info.happyretired.ult;
 
import info.happyretired.db.MySQLiteHelper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 





import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

 





import android.content.Context;
 
public class BlogUtil {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://10.0.2.2/ah_login_api/";
    
    
    // constructor
    public BlogUtil(){
        jsonParser = new JSONParser();
    }
    
  
    
	public JSONArray searchBlogger(String featured){
        // Building Parameters
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "searchBlogger"));
        params.add(new BasicNameValuePair("featured", featured));
        JSONArray json = jsonParser.getJSONArrayFromUrl(CommonConstant.blogUrl, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
    
	public JSONArray searchBlog(String blogger_id, String blogcat_id){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "searchBlog"));
        
        if(blogger_id!=null && !blogger_id.equals(""))
        	params.add(new BasicNameValuePair("blogger_id", blogger_id));
        
        if(blogcat_id!=null && !blogcat_id.equals(""))
        	params.add(new BasicNameValuePair("blogcat_id", blogcat_id));
        
        JSONArray json = jsonParser.getJSONArrayFromUrl(CommonConstant.blogUrl, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
	}
	
	public JSONArray searchBlog(String blogger_id){
        // Building Parameters
    	
        return searchBlog(blogger_id, null);
    }
    
    public JSONArray searchBlogContent(String refNo){
        // Building Parameters
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "searchBlogContent"));
        params.add(new BasicNameValuePair("refNo", refNo));
        JSONArray json = jsonParser.getJSONArrayFromUrl(CommonConstant.blogUrl, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
    
   
     
}