package info.happyretired.activity;
 
import org.json.JSONException;
import org.json.JSONObject;
 






import info.happyretired.HomeActivity;
import info.happyretired.MainActivity;
import info.happyretired.db.MySQLiteHelper;

import com.example.androidhive.library.UserFunctions;
 






import info.happyretired.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPassword2;
    TextView registerErrorMsg;
    ActionBar actionBar = null;
    private Spinner district;
     
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "username";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputPassword2 = (EditText) findViewById(R.id.registerPassword2);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        //btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        district =  (Spinner) findViewById(R.id.district);

        actionBar =  getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true);
         
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String password2 = inputPassword2.getText().toString();
                String districtField = String.valueOf(district.getSelectedItem());
                if(district.getSelectedItemId()==0)
                	districtField = "";
                
                
                if(name.isEmpty()){
                	registerErrorMsg.setText("請輸入登入名稱 ");
                	inputFullName.requestFocus();
                }
                else if(name.length() <3){
                	registerErrorMsg.setText("最少 3 個字 ");
                	inputFullName.requestFocus();
                }
                else if(email.isEmpty()){
                	registerErrorMsg.setText("請輸入電郵");
                	inputEmail.requestFocus();
                }
                else if(password.isEmpty()){
                	registerErrorMsg.setText("請輸入密碼");
                	inputPassword.requestFocus();
                }
                else if(password2.isEmpty()){
                	registerErrorMsg.setText("請輸入確認密碼");
                	inputPassword2.requestFocus();
                }
                else if(password.length() <6 ){
                	registerErrorMsg.setText("最少 6 個字");
                	inputPassword.requestFocus();
                }
                else if(!password.equals(password2)){
                	registerErrorMsg.setText("密碼與確認密碼不相同");
                	inputPassword2.requestFocus();
                }
                else{
	                UserFunctions userFunction = new UserFunctions();
	                JSONObject json = userFunction.registerUser(name, email, password, districtField);
	                 
	                // check for login response
	                try {
	                    if (json.getString(KEY_SUCCESS) != null) {
	                        registerErrorMsg.setText("");
	                        String res = json.getString(KEY_SUCCESS); 
	                        if(Integer.parseInt(res) == 1){
	                            // user successfully registred
	                            // Store user details in SQLite Database
	                        	MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
	                            JSONObject json_user = json.getJSONObject("user");
	                             
	                            // Clear all previous data in database
	                            userFunction.logoutUser(getApplicationContext());
	                            db.addUser(json_user.getString(KEY_USERNAME), json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
	                            // Launch Dashboard Screen
	                            Intent dashboard = new Intent(getApplicationContext(), HomeActivity.class);
	                            // Close all views before launching Dashboard
	                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                            startActivity(dashboard);
	                            // Close Registration Screen
	                            finish();
	                        }else{
	                            // Error in registration
	                        	 if (json.getString(KEY_ERROR) != null) {
	     	                        registerErrorMsg.setText("");
	     	                        res = json.getString(KEY_ERROR); 
	     	                       if(Integer.parseInt(res) == 1){
	     	                    	   registerErrorMsg.setText("電郵已被使用");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 2){
	     	                    	   registerErrorMsg.setText("用戶名稱已被使用 ");
	     	                       }
	                        	 }
	                        	 
	                            
	                        }
	                    }
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
                }
            }
        });
 
        // Link to Login Screen
        /*
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
        */
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
}