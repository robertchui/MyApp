package info.happyretired.activity.forum;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
 








import info.happyretired.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
import info.happyretired.activity.HomeActivity;
import info.happyretired.activity.MainActivity;
import info.happyretired.activity.RegisterActivity;
import info.happyretired.db.MySQLiteHelper;
import info.happyretired.ult.ForumWebserviceUtil;
import info.happyretired.ult.UserFunctionsUtil;

 
public class ReplyActivity extends Activity {
    Button btnSend;
    Button btnBack;
    EditText messageText;
    EditText replyTopicText;
    TextView loginErrorMsg;
    ArrayList  inputArray= null;
    HashMap map = new HashMap();
    
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
        setContentView(R.layout.forum_reply);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        inputArray = intent.getParcelableArrayListExtra ("para");
        
        replyTopicText = (EditText) findViewById(R.id.replyTopic);
        messageText = (EditText) findViewById(R.id.replymessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnBack = (Button) findViewById(R.id.btnBack);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        map = (HashMap)inputArray.get(0);
        String topic = (String)map.get("subject");
        replyTopicText.setText(topic);
        messageText.setText((String)map.get("content"));

        messageText.requestFocus();
        messageText.setSelection(messageText.getText().length());
        
        btnBack.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	 onBackPressed();
            }
        });
        
        // Login button Click Event
        btnSend.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String message = messageText.getText().toString();
                ForumWebserviceUtil forumFunction = new ForumWebserviceUtil();
                
                // check for login response
                if(message.isEmpty()){
                	loginErrorMsg.setText("�п�J�^�Ф��e ");
                	messageText.requestFocus();
                }
                else if(message.length() > 250){
                	loginErrorMsg.setText("�̦h�u��W��250�r ");
                	messageText.requestFocus();
                }
                else{
                	String returnMessage ="";
	                try {
	                	JSONObject json = forumFunction.reply(Integer.parseInt((String)map.get("thread_id")), Integer.parseInt((String)map.get("parent")), Integer.parseInt((String)map.get("user_id")), (String)map.get("subject"), message);
	                    if (json.getString("returnCode") != null) {
	                        loginErrorMsg.setText("");
	                        String res = json.getString("returnCode");
	                        returnMessage = json.getString("returnMessage");
	                        if(res.equals("success")){
	                        	loginErrorMsg.setText("���\�^��"); 
	                            Intent returnIntent = new Intent();
	                            setResult(RESULT_OK,returnIntent);   
	                            finish();
	                        }else{
	                            // Error in login
	                        	if(returnMessage.equals("Contains banned words")){
	                        		loginErrorMsg.setText("����W���t������/����r��");
	                            	messageText.requestFocus();
	                        	}
	                        	else if(returnMessage.equals("Wait a while and repost")){
	                        		loginErrorMsg.setText("�ݵ��ݤ@�|�~��A���^��...");
	                            	messageText.requestFocus();
	                        	}
	                        	else if(returnMessage.equals("User is invalid")){
	                        		loginErrorMsg.setText("�Τ�L�ĩΤw�Q����");
	                            	messageText.requestFocus();
	                        	}
	                        	else{
	                        		loginErrorMsg.setText(returnMessage + " - ���p���t�κ޲z��");
	                        		messageText.requestFocus();
	                        	}
	                        }
	                    }
	                } catch (JSONException e) {
	                    //e.printStackTrace();
	                	 
	                    loginErrorMsg.setText(returnMessage);
	                }
                }
                
            }
        });
 
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
}