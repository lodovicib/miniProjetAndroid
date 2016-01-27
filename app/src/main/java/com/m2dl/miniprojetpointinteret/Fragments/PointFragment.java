package com.m2dl.miniprojetpointinteret.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;

import java.io.File;

public class PointFragment  extends Fragment implements View.OnClickListener {

    private View view;
    private Preferences pref;
    private Bundle saved;
    private FragmentActivity activity;

    public PointFragment() {
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
        activity = PointFragment.this.getActivity();

        Button b = (Button) view.findViewById(R.id.buttonDeletePoint);
        b.setOnClickListener(this);

        pref = new Preferences(this.getActivity());
        Marker marker = pref.getCurrentPoint();

        TextView titleMarker = (TextView) view.findViewById(R.id.titleMarker);
        titleMarker.setText(marker.getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageMarker);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        if (photo.exists()) {
            imageView.setImageURI(Uri.fromFile(photo));
        }

        TextView textTag = (TextView) view.findViewById(R.id.textTag);
        textTag.setText("Tag(s) : "+ marker.getSnippet());

        TextView textType = (TextView) view.findViewById(R.id.textType);
        textType.setText("Type : Point");

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDeletePoint:
                alertBoxSur();
                break;
        }
    }

    public void alertBoxSur() {
        final EditText txtUrl = new EditText(this.getActivity());
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

        alertDialogBuilder.setTitle("Supprimer");
        alertDialogBuilder
                .setMessage("Etes vous sur ? Cette action est irreversible")
                .setView(txtUrl)
                .setCancelable(false)
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentMap = new MapsFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("OK", true);
                fragmentMap.setArguments(bundle);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragmentMap);
                transaction.commit();
                alertDialog.dismiss();
            }
        });
    }
}
