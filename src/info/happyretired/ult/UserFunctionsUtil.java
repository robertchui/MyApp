package info.happyretired.ult;
 
import info.happyretired.db.MySQLiteHelper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

 



import android.content.Context;
 
public class UserFunctionsUtil {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://10.0.2.2/ah_login_api/";
    private static String loginURL = "https://www.happy-retired.com/commonwebservice";
    private static String registerURL = "https://www.happy-retired.com/commonwebservice";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
     
    // constructor
    public UserFunctionsUtil(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password, String district, String age, String mobileValue, String rec_mobileValue, String rec_emailValue, String device_id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", register_tag));
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("mobile", mobileValue));
        params.add(new BasicNameValuePair("recommendation_mobile", rec_mobileValue));
        params.add(new BasicNameValuePair("recommendation_email", rec_emailValue));
        params.add(new BasicNameValuePair("device_id", device_id));
        /*
        String  s = "";
        try{
        	s = URLEncoder.encode(district, "utf-8");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        */
        params.add(new BasicNameValuePair("district", district));
        params.add(new BasicNameValuePair("age", age));
         
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
    	MySQLiteHelper db = new MySQLiteHelper(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
    	MySQLiteHelper db = new MySQLiteHelper(context);
        db.resetTables();
        return true;
    }
    
    public HashMap getUserDetails(Context context){
    	MySQLiteHelper db = new MySQLiteHelper(context);
    	return db.getUserDetails();
    }
     
}