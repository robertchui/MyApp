package info.happyretired.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
 




import info.happyretired.HomeActivity;
import info.happyretired.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
import info.happyretired.MainActivity;
import info.happyretired.db.MySQLiteHelper;

import com.example.androidhive.library.UserFunctions;
 
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    Button btnForgetPassword;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
 
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
        setContentView(R.layout.login);
        
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnForgetPassword = (Button) findViewById(R.id.forgetpassword);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 
                // check for login response
                if(email.isEmpty()){
                	loginErrorMsg.setText("請輸入登入名稱/電郵 ");
                	inputEmail.requestFocus();
                }
                else if(password.isEmpty()){
                	loginErrorMsg.setText("請輸入密碼 ");
                	inputPassword.requestFocus();
                }
                else{
	                try {
	                    if (json.getString(KEY_SUCCESS) != null) {
	                        loginErrorMsg.setText("");
	                        String res = json.getString(KEY_SUCCESS); 
	                        if(Integer.parseInt(res) == 1){
	                            // user successfully logged in
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
	                             
	                            // Close Login Screen
	                            finish();
	                        }else{
	                            // Error in login
	                            loginErrorMsg.setText("登入資料/密碼不正確");
	                        }
	                    }
	                } catch (JSONException e) {
	                    //e.printStackTrace();
	                    loginErrorMsg.setText("登入資料/密碼不正確");
	                }
                }
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                //finish();
            }
        });
     // Link to Register Screen
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
    			String url = "https://www.happy-retired.com/component/comprofiler/lostpassword";
    			i.setData(Uri.parse(url));
                startActivity(i);
                //finish();
            }
        });
    }
}