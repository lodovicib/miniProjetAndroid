package com.m2dl.miniprojetpointinteret;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.m2dl.miniprojetpointinteret.Fragments.AddFragment;
import com.m2dl.miniprojetpointinteret.Fragments.MapsFragment;
import com.m2dl.miniprojetpointinteret.Fragments.SettingsFragment;
import com.m2dl.miniprojetpointinteret.model.IInterestPointDao;
import com.m2dl.miniprojetpointinteret.model.IUserDao;
import com.m2dl.miniprojetpointinteret.model.InterestPointDaoFirebase;
import com.m2dl.miniprojetpointinteret.model.UserDaoFirebase;
import com.m2dl.miniprojetpointinteret.model.InterestPointService;
import com.m2dl.miniprojetpointinteret.model.UserService;
import com.m2dl.miniprojetpointinteret.utils.IdGenerator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = new MapsFragment();
    FragmentTransaction transaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    Preferences pref;
    FloatingActionButton fab;
    private String login;

    private Firebase database;
    private IInterestPointDao interestPointDao;
    private IUserDao userDao;
    private UserService userService;
    private InterestPointService interestPointService;
    private IdGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        database = new Firebase("https://dazzling-heat-8823.firebaseio.com");
        generator = new IdGenerator();
        userDao = new UserDaoFirebase(database);
        interestPointDao = new InterestPointDaoFirebase(database);
        userService = new UserService(userDao, generator);
        interestPointService = new InterestPointService(interestPointDao, generator);
        interestPointDao.addListener(interestPointService);

        ((MapsFragment) fragment).setInterestPointService(interestPointService);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddFragment();
                ((AddFragment) fragment).setPointService(interestPointService);
                fab.setVisibility(View.INVISIBLE);
                switchFragment();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        pref = new Preferences(this);
        pref.setUserService(userService);
        login = pref.getLogin();
        if (login == null)
            alertBoxPseudo();
        else
            pref.setTextHeader();
        createMapsFragment();
        switchFragment();
    }

    private void createMapsFragment() {
        fragment = new MapsFragment();
        ((MapsFragment) fragment).setInterestPointService(interestPointService);
        
    }

    public void alertBoxPseudo() {
        final EditText txtUrl = new EditText(this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Définir un pseudo");
        alertDialogBuilder
                .setMessage("Veuillez entrez un pseudo afin d\'utiliser toutes les fonctionnalités de l'application")
                .setView(txtUrl)
                .setCancelable(false)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        System.exit(RESULT_OK);
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorTxt = "";
                String newLogin = txtUrl.getText().toString();
                if (txtUrl.getText().length() == 0)
                    errorTxt = "Veuillez remplir le champ";
                else if (newLogin.contains(" "))
                    errorTxt = "Le pseudo ne doit pas contenir d\'espaces";
                if (!errorTxt.equals(""))
                    Toast.makeText(MainActivity.this, errorTxt, Toast.LENGTH_SHORT).show();
                else {
                    // TODO Ajouter à la table
                    pref.setLogin(newLogin);
                    alertDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* Menu sur le coté*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_map) {
            createMapsFragment();
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_addPI) {
            fragment = new AddFragment();
            ((AddFragment) fragment).setPointService(interestPointService);
            fab.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
            fab.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_quit) {
            System.exit(RESULT_OK);
        } else {
            createMapsFragment();
        }
        switchFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
