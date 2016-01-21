package com.m2dl.miniprojetpointinteret.data.DAO;

import com.m2dl.miniprojetpointinteret.data.model.User;

/**
 * Created by Romain on 19/01/2016.
 */
public class UserDaoFirebase implements IUserDao {

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public boolean store(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
