package com.m2dl.miniprojetpointinteret.model;

import com.m2dl.miniprojetpointinteret.model.DAO.IInterestPointDao;
import com.m2dl.miniprojetpointinteret.model.DAO.IUserDao;
import com.m2dl.miniprojetpointinteret.model.DAO.InterestPointDaoFirebase;
import com.m2dl.miniprojetpointinteret.model.DAO.UserDaoFirebase;
import com.m2dl.miniprojetpointinteret.utils.IdGenerator;

/**
 * Created by Romain on 22/01/2016.
 */
public final class BindService {

    private IInterestPointDao interestPointDao;
    private IUserDao userDao;
    private UserService userService;
    private InterestPointService interestPointService;
    private IdGenerator generator;

    private static volatile BindService instance = new BindService();

    private BindService() {
        generator = new IdGenerator();
        userDao = new UserDaoFirebase();
        interestPointDao = new InterestPointDaoFirebase();
        userService = new UserService(userDao, generator);
        interestPointService = new InterestPointService(interestPointDao, generator);
        interestPointDao.addListener(interestPointService);
    }

    public final static BindService getInstance() {
        return instance;
    }

    public final UserService getUserService() {
        return userService;
    }

    public final InterestPointService getInterestPointService() {
        return interestPointService;
    }
}
