package com.m2dl.miniprojetpointinteret.model.DAO;

import com.m2dl.miniprojetpointinteret.model.User;

/**
 * Created by Romain on 21/01/2016.
 */
public interface IUserDao {

    boolean store(User user);

    boolean delete(User user);
}
