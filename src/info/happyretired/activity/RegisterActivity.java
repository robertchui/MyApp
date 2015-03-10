package info.happyretired.activity;
 
import org.json.JSONException;
import org.json.JSONObject;

import info.happyretired.db.MySQLiteHelper;
import info.happyretired.ult.UserFunctionsUtil;


import info.happyretired.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.provider.Settings.Secure;
 
public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPassword2;
    EditText mobile;
    EditText recommandation_email;
    //EditText recommandation_mobile;
    TextView registerErrorMsg;
    TextView link;
    ActionBar actionBar = null;
    CheckBox chkIos;
    private Spinner district;
    private Spinner age;
    Context c;
    
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
        mobile = (EditText) findViewById(R.id.mobile);
        chkIos = (CheckBox) findViewById(R.id.chkIos);
        recommandation_email = (EditText) findViewById(R.id.recommandation_email);
        //recommandation_mobile = (EditText) findViewById(R.id.recommandation_mobile);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        //btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        district =  (Spinner) findViewById(R.id.district);
        age =  (Spinner) findViewById(R.id.age);
        link =  (TextView) findViewById(R.id.register_confirm_link);
        c = getApplicationContext();
        if (link != null) {
        	link.setMovementMethod(LinkMovementMethod.getInstance());
        }
        		
        actionBar =  getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true);
         
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String password2 = inputPassword2.getText().toString();
                String mobileValue = mobile.getText().toString();
                //String rec_mobileValue = recommandation_mobile.getText().toString();
                String rec_mobileValue = "";
                String rec_emailValue = recommandation_email.getText().toString();
                String districtField = String.valueOf(district.getSelectedItem());
                String ageField = String.valueOf(age.getSelectedItem());
                if(district.getSelectedItemId()==0)
                	districtField = "";
                
                if(name.isEmpty()){
                	registerErrorMsg.setText("�п�J�n�J�W�� ");
                	inputFullName.requestFocus();
                }
                else if(name.length() <3){
                	registerErrorMsg.setText("�̤� 3 �Ӧr ");
                	inputFullName.requestFocus();
                }
                else if(email.isEmpty()){
                	registerErrorMsg.setText("�п�J�q�l");
                	inputEmail.requestFocus();
                }
                else if(password.isEmpty()){
                	registerErrorMsg.setText("�п�J�K�X");
                	inputPassword.requestFocus();
                }
                else if(password2.isEmpty()){
                	registerErrorMsg.setText("�п�J�T�{�K�X");
                	inputPassword2.requestFocus();
                }
                else if(password.length() <6 ){
                	registerErrorMsg.setText("�̤� 6 �Ӧr");
                	inputPassword.requestFocus();
                }
                else if(!password.equals(password2)){
                	registerErrorMsg.setText("�K�X�P�T�{�K�X���ۦP");
                	inputPassword2.requestFocus();
                }
                else if(!chkIos.isChecked()){
                	registerErrorMsg.setText("�ЦP�N�H�W���I");
                }
                else{
	                UserFunctionsUtil userFunction = new UserFunctionsUtil();
	                String device_id = Secure.getString(c.getContentResolver(),
                            Secure.ANDROID_ID); 
	                JSONObject json = userFunction.registerUser(name, email, password, districtField, ageField, mobileValue, rec_mobileValue, rec_emailValue, device_id);
	                 
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
	     	                    	   registerErrorMsg.setText("�q�l�w�Q�ϥ�");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 2){
	     	                    	   registerErrorMsg.setText("�Τ�W�٤w�Q�ϥ� ");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 3){
	     	                    	   registerErrorMsg.setText("�p���q�ܤw�Q�ϥ�");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 4){
	     	                    	   registerErrorMsg.setText("�t�Χ䤣�줶�ФH�q�l���n�O");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 5){
	     	                    	   registerErrorMsg.setText("�t�Χ䤣�줶�ФH�q�ܪ��n�O");
	     	                       }
	     	                       else if(Integer.parseInt(res) == 6){
	     	                    	   registerErrorMsg.setText("�C���˸m�u�వ�@���|������");
	     	                       }
	     	                       else{
	     	                    	   registerErrorMsg.setText("���p���t�κ޲z��");
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