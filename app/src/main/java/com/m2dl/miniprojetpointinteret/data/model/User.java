package com.m2dl.miniprojetpointinteret.data.model;

import com.m2dl.miniprojetpointinteret.utils.IdGenerator;

/**
 * Created by Romain on 15/01/2016.
 */
public class User {

    private String id;
    private String name;
    private String mail;

    public User() {/* For firebase */}

    public User(String name, String mail) {
        id = IdGenerator.getIdAsString();
        this.name = name;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public void setName(String name) {
        this.name = name;
    }
}
