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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.m2dl.miniprojetpointinteret.model.BindService;
import com.m2dl.miniprojetpointinteret.model.InterestPoint;
import com.m2dl.miniprojetpointinteret.model.InterestPointListener;
import com.m2dl.miniprojetpointinteret.model.InterestPointService;
import com.m2dl.miniprojetpointinteret.utils.BasicListPoints;
import com.m2dl.miniprojetpointinteret.R;

import java.util.List;

/**
 * Created by lgaleron on 10/01/2016.
 */
public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, InterestPointListener {

    private Bundle saved;
    private static GoogleMap mMap;
    private static Double latitude = 43.560573;
    private static Double longitude = 1.468520;
    private static View view;
    private MarkerOptions newMarker;
    private CircleOptions newZone;
    private BasicListPoints listPoints;
    private String MyPREFERENCES = "parametres";
    private InterestPointService interestPointService;
    private BindService bindService;

    public MapsFragment() {
        super();
    }

    public void setInterestPointService(InterestPointService service) {
        interestPointService = service;
        interestPointService.addListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.activity_maps, container, false);

        // TODO delete this code: new point will be added with the InterestPointListener
        saved = getArguments();
        SharedPreferences sharedpreferences = MapsFragment.this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (saved != null && saved.containsKey("latitude")) {
            LatLng latLong = new LatLng(saved.getDouble("latitude"), saved.getDouble("longitude"));
            if (saved.containsKey("sizeZone")) {
                System.out.println("sizeZone : "+saved.getDouble("sizeZone"));
                newZone = new CircleOptions().center(latLong)
                        .radius(saved.getDouble("sizeZone"));
                //if (mMap != null)
                  //  mMap.addCircle(newZone);
            } else {
                System.out.println("tag : "+ saved.getString("tag"));
                newMarker = new MarkerOptions().position(latLong)
                        .title(saved.getString("tag"))
                        .snippet("Ajouté par : " + sharedpreferences.getString("login", null));
               // if (mMap != null)
                 //   mMap.addMarker(newMarker);
            }
        }
        listPoints = new BasicListPoints();
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerFiltre);
        bindService = BindService.getInstance();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mMap.clear();
                if (spinner.getSelectedItem().toString().equals("Tous"))
                    addAllMarker();
                else {
                    for (MarkerOptions m : listPoints.getListPoints()) {
                        String current = m.getTitle().toLowerCase();
                        if (current.contains(spinner.getSelectedItem().toString().toLowerCase()))
                            mMap.addMarker(m);
                    }
                    for (PolygonOptions p : listPoints.getListPolyPoints(spinner.getSelectedItem().toString().toLowerCase())) {
                        mMap.addPolygon(p);
                    }
                }
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

    public void addAllMarker() {
        for (MarkerOptions m : listPoints.getListPoints()) {
            mMap.addMarker(m);
        }
        for (PolygonOptions p : listPoints.getListPolyPoints()) {
            mMap.addPolygon(p);
        }
    }

    private void setUpMap(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getActivity().getLayoutInflater()));
        LatLng latLng = new LatLng(latitude, longitude);
        addAllMarker();
        if (newMarker != null) {
//            mMap.addMarker(newMarker);
            latLng = newMarker.getPosition();
        } else if (newZone != null) {
           // mMap.addCircle(newZone);
            latLng = newZone.getCenter();
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
        return false;
    }

    @Override
    public void onPointsCreated(List<InterestPoint> interestPoints) {
        for (InterestPoint point : interestPoints) {
            System.out.println(point);
            String tags = "";
            for (String tag : point.getTags()) {
                tags += tag + " ";
            }
            MarkerOptions newMarker = new MarkerOptions().position(new LatLng(point.getLatitude(), point.getLongitude()))
                    .title(tags)
                    .snippet("Ajouté par : " + point.getUserName());
            mMap.addMarker(newMarker);
        }
    }

    @Override
    public void onReadPointError() {
        // alert user or not...
    }


}
