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
    private String username;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getTags() {
        return tags;
    }

    public long getDate() {
        return date;
    }
}
