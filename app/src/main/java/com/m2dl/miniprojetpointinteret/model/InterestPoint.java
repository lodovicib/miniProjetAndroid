package com.m2dl.miniprojetpointinteret.model;

import java.util.List;

/**
 * Created by Romain on 15/01/2016.
 */
public class InterestPoint {

    private String id;
    private double longitude;
    private double latitude;
    private double radius;
    private String photo;
    private String userName;
    private List<String> tags;
    private long date;

    public InterestPoint() {/* For firebase */}

    void setId(String id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPhoto(String photoUrl) {
        this.photo = photoUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
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

    public String getPhotoUrl() {
        return photo;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getTags() {
        return tags;
    }

    public long getDate() {
        return date;
    }
}
