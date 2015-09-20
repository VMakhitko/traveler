package com.team.traveler.places;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.team.traveler.utils.Http;

import java.io.IOException;

/**
 * Created by kasper on 9/17/15.
 */
public class GooglePlaceReadTask extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    GoogleMap map;

    @Override
    protected String doInBackground(Object... inObj) {
        try {
            map = (GoogleMap) inObj[0];
            String googlePlacesUrl = (String) inObj[1];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        ProcessDisplayTask placesDisplayTask = new ProcessDisplayTask();
        Object[] toPass = new Object[2];
        toPass[0] = map;
        toPass[1] = result;
        placesDisplayTask.execute(toPass);
    }
}
