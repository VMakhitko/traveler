package com.team.traveler.places;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kasper on 9/8/15.
 */
public class Place {
    private String name;
    private Bitmap icon;
    private String id;
    private String place_id;
    private String reference;
    private String scope;
    private String types;
    private String vicinity;
    private Location location;
    private LatLng latLng;

    Place() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void  setReference(String reference) {
        this.reference = reference;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return this.name;
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public String getId() {
        return this.id;
    }

    public String getPlace_id() {
        return this.place_id;
    }

    public String getReference() {
        return this.reference;
    }

    public String getScope() {
        return this.scope;
    }

    public String getTypes() {
        return this.types;
    }

    public String getVicinity() {
        return this.vicinity;
    }

    public Location getLocation() {
        return this.location;
    }

    public LatLng getLatLng() {
        return this.latLng;
    }
}
