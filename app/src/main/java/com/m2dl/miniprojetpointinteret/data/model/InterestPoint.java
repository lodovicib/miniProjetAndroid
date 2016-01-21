package com.m2dl.miniprojetpointinteret.data.model;

import java.util.List;

/**
 * Created by Romain on 15/01/2016.
 */
public class InterestPoint {

    private String id;
    private GeoPoint geoPoint;
    private String photoUrl;
    private User user;
    private List<Tag> tags;

    public InterestPoint() {/* For firebase */}

    public InterestPoint(GeoPoint point, byte[] photo, User user, List<Tag> tags) {

    }
}
