package info.happyretired.activity.userprofile;
 
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.happyretired.activity.HomeActivity;
import info.happyretired.activity.LoginActivity;
import info.happyretired.activity.MainActivity;
import info.happyretired.activity.forum.ReplyActivity;
import info.happyretired.db.MySQLiteHelper;
import info.happyretired.ult.UserFunctionsUtil;
import info.happyretired.ult.UserWebserviceUtil;


import info.happyretired.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;
 
public class PersonalDetailsActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText loginField;
    EditText nameField;
    EditText emailField;
    EditText inputPassword;
    EditText inputPassword2;
    EditText telField;
    EditText creditField;
    TextView registerErrorMsg;
    ActionBar actionBar = null;
    Context c;
    GetProfileTask task;
   
    private JSONArray jsonArray = new JSONArray();
	LinearLayout linlaHeaderProgress;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);
 
        // Importing all assets like buttons, text fields
        loginField = (EditText) findViewById(R.id.registerName);
        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        
        telField = (EditText) findViewById(R.id.mobile);
        creditField = (EditText) findViewById(R.id.credit);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        actionBar =  getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true);
        c = this.getApplicationContext();
        task = new GetProfileTask();;
        task.execute();
         
        // Register Button Click event
        OnClickListener clickFunction = new OnClickClass(this, "save");
        btnRegister.setOnClickListener(clickFunction);

    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
    
    public void updateLayout(JSONObject json) throws Exception{
    	String login = json.getString("loginName");
		String name = json.getString("name");
    	String tel = json.getString("tel");
    	String credit = json.getString("credit");
    	String email = json.getString("email");
    	
    	loginField.setText(login);
    	nameField.setText(name);
    	telField.setText(tel);
    	
    	if(credit.equals("null")){
    		creditField.setText("0");
    	}
    	else
    		creditField.setText(credit);
    	
    	emailField.setText(email);
    }
    
    private class GetProfileTask extends AsyncTask<String, Void, String> {
    	
    	JSONObject json;
    	
    	@Override
        protected void onPreExecute() {
    		//mdialog=ProgressDialog.show(getActivity(),"",getActivity().getResources().getString(R.string.please_wait),false);
    		//linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
    	
    	
        @Override
        protected String doInBackground(String... urls) {
        	
        	if(this.isCancelled())
        		return "";
        	
        	UserFunctionsUtil userFunction = new UserFunctionsUtil();
        	HashMap user = userFunction.getUserDetails(c);
        	UserWebserviceUtil userWebserviceUtil = new UserWebserviceUtil();
            json = userWebserviceUtil.getProfileDetails((String)user.get("uid"), (String)user.get("email"));
            
            
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        	Context myContext = c;      
        	
        	if(this.isCancelled())
        		return;

        	//linlaHeaderProgress.setVisibility(View.GONE);
            //mdialog.dismiss();
        	
        	if(json!=null){
            	try{
            		updateLayout(json);
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            	
            }
        }
      }
    
    
    private class OnClickClass implements OnClickListener{
    	HashMap in = new HashMap();
    	Context context;
    	String action="";
    	
    	public OnClickClass(Context context, String action){
    		this.action = action;
    		this.context = context;
    	}
    	
    	public void onClick(View arg0) {
    		
    		Toast.makeText(context, "我們正努力建立此功能", Toast.LENGTH_LONG).show();
         }
     }
}