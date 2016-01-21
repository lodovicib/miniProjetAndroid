package com.m2dl.miniprojetpointinteret.data.model;

/**
 * Created by Romain on 19/01/2016.
 */
public class GeoPoint {

    private double longitude;
    private double latitude;
    private double radius;
    private boolean isZone;

    public GeoPoint() {/* For firebase */}

    public GeoPoint(double longitute, double latitude) {
        this(longitute, latitude, -1);
    }

    public GeoPoint(double longitute, double latitude, double radius) {
        this.longitude = longitute;
        this.latitude = latitude;
        this.radius = radius;
        defineZone();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isZone() {
        return isZone;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        defineZone();
    }

    private void defineZone() {
        if (radius > 0) {
            isZone = true;
        } else {
            isZone = false;
        }
    }
}
