package com.m2dl.miniprojetpointinteret.data.model;

/**
 * Created by Romain on 19/01/2016.
 */
public class Tag {

    String id;
    String name;

    public Tag() {/* For firebase */}

    public Tag(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
