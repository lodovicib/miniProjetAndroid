package com.m2dl.miniprojetpointinteret.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;

import java.io.File;
import java.util.List;

class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Marker marker;
    private View view;
    private List<Marker> markers;
    private Preferences pref;
    private LayoutInflater inflater = null;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
        pref = new Preferences(inflater.getContext().getSharedPreferences("parametres", Context.MODE_PRIVATE));
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View popup = inflater.inflate(R.layout.custom_info_window, null);
        TextView title = (TextView) popup.findViewById(R.id.title);
        TextView snippet = (TextView) popup.findViewById(R.id.snippet);
        ImageView imageView = (ImageView) popup.findViewById(R.id.badge);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        if (marker.getSnippet() != null) {
            File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
            if (photo.exists())
                imageView.setImageURI(Uri.fromFile(photo));
        }
        return popup;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

}
