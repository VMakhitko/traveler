package com.team.traveler;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.team.traveler.provider.GeoInfoProfider;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {
    private final String TAG = MainActivity.class.getSimpleName();

    int PLACE_PICKER_RESULT = 1;

    /*Main 4 buttons*/
    private Button mPlaces;
    private Button mTracks;
    private Button mSearch;
    private Button mMy;

    private GoogleApiClient googleApiClient;
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private LatLng currentLoc;
    private Location lastLocation;


    private GeoInfoProfider geoInfoProfider;

//    private PlacePicker.IntentBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Connect to Google Play Service*/
        buildGoogleApiClient();

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

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .addApi(Places.GEO_DATA_API)
            .addApi(Places.PLACE_DETECTION_API)
            .build();
    }

    private void initMap() {
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
        map = supportMapFragment.getMap();
        map.setMyLocationEnabled(true);
        GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                Log.d(TAG, "onMyLocationChange");
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
//                if (currentLoc != null) {
//                    geoInfoProfider.setCircleMarkers(map, currentLoc);
//                }
                /*TODO: Debug PlacePicker. It crashes in some reason :(*/
//                try {
//                    builder = new PlacePicker.IntentBuilder();
//                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_RESULT);
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                }

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

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            Log.d(TAG, "lastLocation: " + lastLocation.toString());
            /*TODO: Update somesing ^)*/
        } else {
            Toast.makeText(this, "No location detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_RESULT) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, MainActivity.this);
//                String toast = String.format("Place: %s", place.getName());
//                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
