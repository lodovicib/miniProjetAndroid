package com.m2dl.miniprojetpointinteret.model;

/**
 * Created by Romain on 15/01/2016.
 */
public class User {

    private String id;
    private String name;

    public User() {/* For firebase */}

    public User(String name) {
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

    void setId(String id) {
        this.id = id;
    }
}
