package com.m2dl.miniprojetpointinteret.model;

import com.firebase.client.Firebase;

public class UserDaoFirebase implements IUserDao {

    private final Firebase database;

    public UserDaoFirebase(Firebase database) {
        this.database = database.child("users");
    }

    @Override
    public boolean store(User user) {
        database.child(user.getId()).setValue(user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        database.child(user.getId()).removeValue();
        return true;
    }
}
