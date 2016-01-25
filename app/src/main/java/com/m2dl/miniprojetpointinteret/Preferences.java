package com.m2dl.miniprojetpointinteret;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.m2dl.miniprojetpointinteret.model.BindService;
import com.m2dl.miniprojetpointinteret.model.User;

/**
 * Created by lgaleron on 22/01/2016.
 */
public class Preferences {

    SharedPreferences sharedpreferences;
    private String MyPREFERENCES = "parametres";
    NavigationView navigationView;
    TextView textLogin;
    View headView;
    BindService bindService;

    public Preferences(MainActivity main) {
        sharedpreferences = main.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        bindService = new BindService();
        init();
    }

    public Preferences(FragmentActivity main) {
        sharedpreferences = main.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        init();
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
            user = bindService.getUserService().createUser(newLogin);
            editor.putString("id", user.getId());
        } else
            user = bindService.getUserService().changeName(getId(), newLogin);
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
