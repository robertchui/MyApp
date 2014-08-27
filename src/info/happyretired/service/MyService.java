package info.happyretired.service;

import info.happyretired.R;
import info.happyretired.activity.blog.BlogTabsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	@Override
    public IBinder onBind(Intent intent) {
           // TODO: Return the communication channel to the service.
           throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
           // TODO Auto-generated method stub

           Toast.makeText(getApplicationContext(), "Service Created", 1).show();
           super.onCreate();
    }

    @Override
    public void onDestroy() {
           // TODO Auto-generated method stub
           Toast.makeText(getApplicationContext(), "Service Destroy", 1).show();
           super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
           // TODO Auto-generated method stub
           Toast.makeText(getApplicationContext(), "Service Running ", 1).show();
           return super.onStartCommand(intent, flags, startId);
    }

	}