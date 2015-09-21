package com.team.traveler.places;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kasper on 9/17/15.
 */
public class Places {
    private final String TAG = Places.class.getSimpleName();

    public List<HashMap<String, String>> parseGoogle(JSONObject jsonObject) {
        JSONArray jsonArray = null;
        try {
            Log.d(TAG, "JSON Object: " + jsonObject.toString());
            jsonArray = jsonObject.getJSONArray("results");
            if (jsonArray == null) {
                Log.e(TAG, "JSON Array equal null");
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlacesGoogle(jsonArray);
    }

    private List<HashMap<String, String>> getPlacesGoogle(JSONArray jsonArray) {
        int placeCount = jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < placeCount; i++) {
            try {
                placeMap = getPlaceGoogle((JSONObject) jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeList;
    }

    private HashMap<String, String> getPlaceGoogle(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    public List<HashMap<String, String>> parseWiki(JSONObject jsonObject) {
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("geonames");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlacesWiki(jsonArray);
    }

    private List<HashMap<String, String>> getPlacesWiki(JSONArray jsonArray) {
        int placeCount = jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < placeCount; i++) {
            try {
                placeMap = getPlaceWiki((JSONObject)jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeList;
    }

    private HashMap<String, String> getPlaceWiki(JSONObject wikiJson) {
        HashMap<String, String> wikiPlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!wikiJson.isNull("title")) {
                placeName = wikiJson.getString("title");
            }
            latitude = wikiJson.getString("lat");
            longitude = wikiJson.getString("lng");

            wikiPlaceMap.put("place_name", placeName);
            wikiPlaceMap.put("lat", latitude);
            wikiPlaceMap.put("lng", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wikiPlaceMap;
    }
}
