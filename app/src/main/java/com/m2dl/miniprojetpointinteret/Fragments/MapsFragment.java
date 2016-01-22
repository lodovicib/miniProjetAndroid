package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.m2dl.miniprojetpointinteret.utils.BasicListPoints;
import com.m2dl.miniprojetpointinteret.R;

/**
 * Created by lgaleron on 10/01/2016.
 */
public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener{ //,  View.OnClickListener

    private Bundle saved;
    private static GoogleMap mMap;
    private static Double latitude = 43.560573;
    private static Double longitude = 1.468520;
    private static View view;
    private MarkerOptions newMarker;
    private BasicListPoints listPoints;
    private String MyPREFERENCES = "parametres";

    public MapsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.activity_maps, container, false);

        saved = getArguments();
        SharedPreferences sharedpreferences = MapsFragment.this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (saved != null && saved.containsKey("latitude"))
            newMarker = new MarkerOptions().position(new LatLng(saved.getDouble("latitude"), saved.getDouble("longitude")))
                    .title(saved.getString("tag"))
                    .snippet("Ajouté par : "+ sharedpreferences.getString("login", null));
        listPoints = new BasicListPoints();
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerFiltre);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                System.out.println(spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    setUpMap(googleMap);
                }
            });
        }
    }

    private void setUpMap(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        LatLng latLng = new LatLng(latitude, longitude);
        for (MarkerOptions m : listPoints.getListPoints()) {
            mMap.addMarker(m);
        }
        for (PolygonOptions p : listPoints.getListPolyPoints()) {
            mMap.addPolygon(p);
        }
        if (newMarker != null) {
            mMap.addMarker(newMarker);
            latLng = newMarker.getPosition();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMap = null;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setRetainInstance(true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(MapsFragment.this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinnerFiltre:
        }
    }*/
}
