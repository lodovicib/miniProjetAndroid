package com.m2dl.miniprojetpointinteret;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.m2dl.miniprojetpointinteret.model.User;
import com.m2dl.miniprojetpointinteret.model.UserService;

/**
 * Created by lgaleron on 22/01/2016.
 */
public class Preferences {

    SharedPreferences sharedpreferences;
    private String MyPREFERENCES = "parametres";
    NavigationView navigationView;
    TextView textLogin;
    View headView;
    private UserService userService;

    public Preferences(MainActivity main) {
        sharedpreferences = main.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        init();
    }

    public Preferences(FragmentActivity main) {
        sharedpreferences = main.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        init();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void init() {
        headView = navigationView.getHeaderView(0);
        textLogin = ((TextView) headView.findViewById(R.id.login));
    }

    public String getLogin() {
        return sharedpreferences.getString("login", null);
    }

    public void setLogin(String newLogin) {
        User user;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (getId() == null) {
            user = userService.createUser(newLogin);
            editor.putString("id", user.getId());
        } else
            user = userService.changeName(getId(), newLogin);
        editor.putString("login", user.getName());
        editor.apply();
        setTextHeader();
    }

    public String getId() {
        return sharedpreferences.getString("id", null);
    }

    public void setTextHeader() {
        String login = getLogin();
        if (login != null)
            textLogin.setText(login);
    }

}
