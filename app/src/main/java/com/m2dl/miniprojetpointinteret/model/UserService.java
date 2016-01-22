package com.m2dl.miniprojetpointinteret.model;

import com.m2dl.miniprojetpointinteret.model.DAO.IUserDao;
import com.m2dl.miniprojetpointinteret.utils.IdGenerator;

/**
 * Created by Romain on 15/01/2016.
 */
public class UserService {

    private IUserDao userDao;
    private IdGenerator generator;

    public UserService(IUserDao userDao, IdGenerator generator) {
        this.generator = generator;
        this.userDao = userDao;
    }

    public User createUser(String name) {
        return changeName(generator.getIdAsString(), name);
    }

    public User changeName(String userId, String name) {
        User user = new User(name);
        user.setId(userId);
        if (!userDao.store(user)) {
            // TODO throw an exception
        }
        return user;
    }

    public boolean delete(User user) {
        // TODO implement it
        return false;
    }
}
