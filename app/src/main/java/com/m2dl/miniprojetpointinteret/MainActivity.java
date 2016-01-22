package com.m2dl.miniprojetpointinteret;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.m2dl.miniprojetpointinteret.Fragments.AddFragment;
import com.m2dl.miniprojetpointinteret.Fragments.MapsFragment;
import com.m2dl.miniprojetpointinteret.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = new MapsFragment();
    FragmentTransaction transaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    Preferences pref;
    private String login;
    TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pref = new Preferences(this);
        login = pref.getLogin();
        if (login == null)
            alertBoxPseudo();
        else
            pref.setTextHeader();
        fragment = new MapsFragment();
        switchFragment();
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
                    pref.setLogin(newLogin);
                    // TODO Ajouter à la table
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* Barre en haut */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Menu sur le coté*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_map) {
            fragment = new MapsFragment();
        } else if (id == R.id.nav_addPI) {
            fragment = new AddFragment();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_quit) {
            System.exit(RESULT_OK);
        } else {
            fragment = new MapsFragment();
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
