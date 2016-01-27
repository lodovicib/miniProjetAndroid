package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;
import com.m2dl.miniprojetpointinteret.model.InterestPoint;
import com.m2dl.miniprojetpointinteret.model.InterestPointListener;
import com.m2dl.miniprojetpointinteret.model.InterestPointService;
import com.m2dl.miniprojetpointinteret.utils.BasicListPoints;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, InterestPointListener {

    private Bundle saved;
    private static GoogleMap mMap;
    private static Double latitude = 43.560573;
    private static Double longitude = 1.468520;
    private static View view;
    private MarkerOptions newMarker;
    private CircleOptions newZone;
    private BasicListPoints listPoints;
    private InterestPointService interestPointService;
    private Preferences pref;
    private List<MarkerOptions> stayedPoint = new ArrayList<>();

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
        pref = new Preferences(this.getActivity());

        saved = getArguments();
        if (saved != null && saved.containsKey("latitude")) {
            LatLng latLong = new LatLng(saved.getDouble("latitude"), saved.getDouble("longitude"));
            if (saved.containsKey("sizeZone")) {
                System.out.println("sizeZone : " + saved.getDouble("sizeZone"));
                newZone = new CircleOptions().center(latLong)
                        .radius(saved.getDouble("sizeZone"));
            } else {
                System.out.println("tag : " + saved.getString("tag"));
                newMarker = new MarkerOptions().position(latLong)
                        .title(saved.getString("tag"))
                        .snippet("Ajouté par : " + pref.getLogin());
                BitmapDescriptor color = getColorMarker(saved.getString("tag"));
                if (color != null)
                    newMarker.icon(color);
            }
        }

        if (pref.getCurrentPoint() != null) {
            pref.getCurrentPoint().setVisible(false);
            pref.setCurrentPoint(null);
        }
        if (mMap == null) {
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    Log.e("setupMap", "je setup la map");
                    setUpMap(googleMap);
                }
            });
        }
        listPoints = new BasicListPoints();

        ImageButton help = (ImageButton) view.findViewById(R.id.imageButton);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.help_dialog);
                dialog.setTitle("Légende de la carte");
                // set the custom dialog components - text, image and button
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private BitmapDescriptor getColorMarker(String tag) {
        if (tag.contains("Recyclage : Verre"))
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        if (tag.contains("Recyclage : Textille"))
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
        return null;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerFiltre);
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
                Button b = (Button) view.findViewById(R.id.buttonDetails);
                b.setVisibility(View.VISIBLE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new PointFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.commit();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
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
            latLng = newMarker.getPosition();
        } else if (newZone != null) {
            latLng = newZone.getCenter();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        if (!stayedPoint.isEmpty()) {
            for (MarkerOptions mark : stayedPoint) {
                mMap.addMarker(mark);
            }
            stayedPoint.clear();
        }
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
        interestPointService.removeListener(this);
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
            String tags = "";
            for (String tag : point.getTags()) {
                tags += tag + " ";
            }
            MarkerOptions newMarker = new MarkerOptions().position(new LatLng(point.getLatitude(), point.getLongitude()))
                    .title(tags)
                    .snippet("Ajouté par : " + point.getUsername());
            if (mMap == null) {
                stayedPoint.add(newMarker);
            } else {
                mMap.addMarker(newMarker);
            }
            Log.e("mmap", "mmpa null");

        }
    }

    @Override
    public void onReadPointError() {
    }

}
