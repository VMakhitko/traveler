package com.team.traveler.provider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by x0158990 on 03.09.15.
 */
public class GeoInfoProfider {
    private final String TAG = GeoInfoProfider.class.getSimpleName();

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
}
