package com.m2dl.miniprojetpointinteret.model.DAO;

import com.firebase.client.Firebase;
import com.m2dl.miniprojetpointinteret.model.User;

/**
 * Created by Romain on 19/01/2016.
 */
public class UserDaoFirebase implements IUserDao {

    private static final Firebase DATABASE = new Firebase("https://dazzling-heat-8823.firebaseio.com/users");

    @Override
    public boolean store(User user) {
        DATABASE.child(user.getId()).setValue(user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        DATABASE.child(user.getId()).removeValue();
        return true;
    }
}
