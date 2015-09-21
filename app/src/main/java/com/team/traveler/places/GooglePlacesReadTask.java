package com.team.traveler.places;

import android.util.Log;

/**
 * Created by kasper on 9/17/15.
 */
public class GooglePlacesReadTask extends PlaceReadTask {
    private final String TAG = GooglePlacesReadTask.class.getSimpleName();

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, " result: " + result);
        ProcessGoogleDisplayTask placesDisplayTask = new ProcessGoogleDisplayTask();
        Object[] toPass = new Object[2];
        toPass[0] = super.map;
        toPass[1] = result;
        placesDisplayTask.execute(toPass);
    }
}
