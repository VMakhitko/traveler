package com.team.traveler;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.team.traveler.provider.GeoInfoProfider;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String TAG = MainActivity.class.getSimpleName();

    /*Main 4 buttons*/
    private Button mPlaces;
    private Button mTracks;
    private Button mSearch;
    private Button mMy;

    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private LatLng currentLoc;

    private GeoInfoProfider geoInfoProfider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Init map*/
        initMap();
        /*Init buttons*/
        initButtons();

        /*Add markers*/
        geoInfoProfider = new GeoInfoProfider();
        if (currentLoc == null) Log.d(TAG, "currentLoc == null");

    }

    @Override
    public void onMapReady(GoogleMap map) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initMap() {
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
        map = supportMapFragment.getMap();
        map.setMyLocationEnabled(true);
        GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.d(TAG, "onMyLocationChange");
                currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 16.0f));
            }
        };
        map.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }

    private void initButtons() {
        mPlaces = (Button) findViewById(R.id.butt_places);
        mPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Places button clicked");
                map.clear();
                if (currentLoc != null) {
                    geoInfoProfider.setCircleMarkers(map, currentLoc);
                }
            }
        });

        mTracks = (Button) findViewById(R.id.butt_tracks);
        mTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Tracks button clicked");
                map.clear();
                /*TODO: Add polylines*/
            }
        });

        mSearch = (Button) findViewById(R.id.butt_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Search button clicked");
            }
        });

        mMy = (Button) findViewById(R.id.butt_my);
        mMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "My button clicked");
            }
        });
    }
}
