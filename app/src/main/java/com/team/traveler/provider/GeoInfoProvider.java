package com.team.traveler.provider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team.traveler.places.GooglePlacesReadTask;
import com.team.traveler.places.WikiPlacesReadTask;

/**
 * Created by x0158990 on 03.09.15.
 */
public class GeoInfoProvider {
//    private final String GOOGLE_API_KEY = "AIzaSyDdESl_6pKf0lrRUzeWjmgrK6oBTc92Q18";
    private final String GOOGLE_API_KEY = "AIzaSyBlHan6h_AtjBYRTaEpXY8IBCDhU5M7_N8";

    private final String TAG = GeoInfoProvider.class.getSimpleName();

    public void setCircleMarkers(GoogleMap map, LatLng location) {
        /*Add first marker*/
        LatLng posFirst = new LatLng(location.latitude + 0.01, location.longitude);
        BitmapDescriptor iconFirst = BitmapDescriptorFactory.defaultMarker(9);
        boolean draggableFirst = true;
        MarkerOptions optionsFirst = new MarkerOptions().position(posFirst).icon(iconFirst).draggable(draggableFirst);

        map.addMarker(optionsFirst);

        double rx = posFirst.latitude - location.latitude;
        double ry = posFirst.longitude - location.longitude;


        for (int degree = 9; degree < 360; degree += 18) {
            double rad = Math.toRadians(degree);

            LatLng position = new LatLng(location.latitude + rx * Math.cos(rad) - ry * Math.sin(rad),
                    location.longitude + rx * Math.sin(rad) + ry * Math.cos(rad));
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(degree);
            boolean draggable = true;
            MarkerOptions options = new MarkerOptions().position(position).icon(icon).draggable(draggable);

            map.addMarker(options);
        }
    }

    public void findLocalGooglePlaces(GoogleMap map, LatLng latLng, int radius, int types, boolean sensor) {
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latLng.latitude + "," + latLng.longitude);
        googlePlacesUrl.append("&radius=" + radius);
        googlePlacesUrl.append("&types=" + types);
        googlePlacesUrl.append("&sensor=" + Boolean.toString(sensor));
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        /*Async Task for call places from Google*/
        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[2];
        toPass[0] = map;
        toPass[1] = googlePlacesUrl.toString();

        googlePlacesReadTask.execute(toPass);
    }

    public void findLocalWikiPlaces(GoogleMap map, LatLng latLng, int radius) {
        StringBuilder wikiPlacesUrl = new StringBuilder("http://api.geonames.org/findNearbyWikipediaJSON?");
        wikiPlacesUrl.append("lat=" + latLng.latitude + "&lng=" + latLng.longitude);
        wikiPlacesUrl.append("&username=traveler");

        WikiPlacesReadTask wikiPlacesReadTask = new WikiPlacesReadTask();
        Object[] toPass = new Object[2];
        toPass[0] = map;
        toPass[1] = wikiPlacesUrl.toString();
        wikiPlacesReadTask.execute(toPass);
    }
}
