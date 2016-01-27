package com.m2dl.miniprojetpointinteret.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Preferences pref;
    private EditText login;
    private FragmentActivity activity;

    public SettingsFragment() {
        super();
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.settings, container, false);
        activity = SettingsFragment.this.getActivity();
        pref = new Preferences(activity);
        login = (EditText) view.findViewById(R.id.editLogin);
        login.setText(pref.getLogin());

        Button b = (Button) view.findViewById(R.id.buttonModifier);
        b.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonModifier:
                String errorTxt = "";
                String newLogin = login.getText().toString();
                if (login.getText().length() == 0)
                    errorTxt = "Veuillez remplir le champ";
                else if (newLogin.contains(" "))
                    errorTxt = "Le pseudo ne doit pas contenir d\'espaces";
                if (!errorTxt.equals(""))
                    Toast.makeText(activity, errorTxt, Toast.LENGTH_SHORT).show();
                else {
                    pref.setLogin(newLogin);
                    Toast.makeText(activity, "Modification effectuée", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
