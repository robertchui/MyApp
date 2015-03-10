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
 
public class UserWebserviceUtil {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://10.0.2.2/ah_login_api/";
    
    private static String getUserProfile = "getUserProfile";
    
     
    // constructor
    public UserWebserviceUtil(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject getProfileDetails(String user_id, String email){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", getUserProfile));
        params.add(new BasicNameValuePair("user_id", user_id));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(CommonConstant.commonUrl, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
   
     
}