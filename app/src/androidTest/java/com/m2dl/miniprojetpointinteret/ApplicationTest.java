package com.m2dl.miniprojetpointinteret;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.m2dl.miniprojetpointinteret.model.IUserDao;
import com.m2dl.miniprojetpointinteret.model.UserDaoFirebase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
        IUserDao repos = new UserDaoFirebase();
        //UserService service = new UserService(repos);

        //service.createUser("test");
    }
}