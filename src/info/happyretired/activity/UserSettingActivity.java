package info.happyretired.activity;

import info.happyretired.R;
import info.happyretired.ult.CommonConstant;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class UserSettingActivity extends PreferenceActivity 
{
             
	
             @Override
             public void onCreate(Bundle savedInstanceState) 
             {
                     super.onCreate(savedInstanceState);
                      // add the xml resource                     
                    //setContentView(R.layout.user_setting);
                     addPreferencesFromResource(R.layout.user_setting);
                     updateFontSize("");
                     
                  // Get the custom preference
                     Preference customPref = (Preference) findPreference(CommonConstant.FONT_SIZE);
                     customPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                     public boolean onPreferenceChange(Preference preference, Object newValue) {
                    	  updateFontSize(String.valueOf(newValue));
                    	  return true;
                     }
                     });

             }
             
           
             
             public void updateFontSize(String fontsize){
            	 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            	 
            	 if(fontsize ==null || fontsize.equals(""))
            		 fontsize = sharedPrefs.getString(CommonConstant.FONT_SIZE, CommonConstant.FONT_SIZE_DEFAULT);
                 
                 Preference customPref = (Preference) findPreference(CommonConstant.FONT_SIZE);
                 
                 String fontLabel ="�j";
                 if(fontsize.equals("24")){
                	 fontLabel="�S�j";
                 }
                 else if(fontsize.equals("22")){
                	 fontLabel="�j";
                 }
                 else if(fontsize.equals("20")){
                	 fontLabel="��";
                 }
                 else if(fontsize.equals("18")){
                	 fontLabel="�p";
                 }
                 customPref.setTitle("�r��j�p: "+fontLabel);
             }

} 