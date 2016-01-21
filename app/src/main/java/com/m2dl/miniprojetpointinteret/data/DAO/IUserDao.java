package com.m2dl.miniprojetpointinteret.data.DAO;

import com.m2dl.miniprojetpointinteret.data.model.User;

/**
 * Created by Romain on 21/01/2016.
 */
public interface IUserDao {

    User findById(String id);

    boolean store(User user);

    boolean delete(User user);
}
