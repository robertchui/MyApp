package info.happyretired.activity;

import info.happyretired.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MapActivity extends Activity {
	 
    // Google Map
    private GoogleMap googleMap;
    double latitude;
    double longitude;
    String address;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        address = intent.getStringExtra("address");
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
    
    @Override
   	public boolean onOptionsItemSelected(MenuItem menuItem)
   	{       
   	    onBackPressed();
   	    return true;
   	}

 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            else{
            	
            	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            	googleMap.setMyLocationEnabled(true);
            	
                LatLng srcDest = new LatLng(latitude, longitude);
                Marker hamburg = googleMap.addMarker(new MarkerOptions().position(srcDest)
                        .title(address));
                hamburg.showInfoWindow();
                
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(srcDest, 10));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        }
        
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
}