package com.team.traveler.places;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kasper on 9/17/15.
 */
public class ProcessDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {
    JSONObject googlePlacesJSON;
    GoogleMap map;

    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inObj) {
        List<HashMap<String, String>> googlePlacesList = null;
        Places placeJsonParser = new Places();

        try {
            map = (GoogleMap) inObj[0];
            googlePlacesJSON = new JSONObject((String) inObj[1]);
            googlePlacesList = placeJsonParser.parse(googlePlacesJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {
        map.clear();
        for (HashMap<String, String> googlePlace : list) {
            MarkerOptions markerOptions = new MarkerOptions();
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + ":" + vicinity);
            map.addMarker(markerOptions);
        }
    }
}
