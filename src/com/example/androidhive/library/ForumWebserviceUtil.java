package com.example.androidhive.library;
 
import info.happyretired.db.MySQLiteHelper;
import info.happyretired.ult.CommonConstant;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 




import android.content.Context;
 
public class ForumWebserviceUtil {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://10.0.2.2/ah_login_api/";
    
    private static String reply_tag = "replyMessage";
    
     
    // constructor
    public ForumWebserviceUtil(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject reply(int thread_id, int parent, int user_id, String subject, String content){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", reply_tag));
        params.add(new BasicNameValuePair("thread_id", Integer.toString(thread_id)));
        params.add(new BasicNameValuePair("parent", Integer.toString(parent)));
        params.add(new BasicNameValuePair("user_id", Integer.toString(user_id)));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("content", content));
        JSONObject json = jsonParser.getJSONFromUrl(CommonConstant.forumUrl, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
   
     
}