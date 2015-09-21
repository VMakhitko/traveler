package com.team.traveler.places;

import android.util.Log;

/**
 * Created by kasper on 9/21/15.
 */
public class WikiPlacesReadTask extends PlaceReadTask {
    private final String TAG = WikiPlacesReadTask.class.getSimpleName();

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "Wiki result: " + result);
        ProcessWikiDisplayTask placeDisplayTask = new ProcessWikiDisplayTask();
        Object[] toPass = new Object[2];
        toPass[0] = super.map;
        toPass[1] = result;
        placeDisplayTask.execute(toPass);
    }
}
