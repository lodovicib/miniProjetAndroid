package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2dl.miniprojetpointinteret.BasicListPoints;
import com.m2dl.miniprojetpointinteret.R;

/**
 * Created by lgaleron on 10/01/2016.
 */
public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener {

    private Bundle saved;
    private static GoogleMap mMap;
    private static Double latitude = 43.560573, longitude = 1.468520;
    private static View view;
    MarkerOptions newMarker;
    private BasicListPoints listPoints;
    private String MyPREFERENCES = "parametres";

    public MapsFragment() {
        super();
       // setArguments(new Bundle());
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
        if (newMarker != null) {
            mMap.addMarker(newMarker);
            latLng = newMarker.getPosition();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        enableMyLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //if (mMap != null) {
            mMap = null;
        //}
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setRetainInstance(true);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(MapsFragment.this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private class LocationHandler implements LocationListener {

        private static final String TAG = "LocationOverlay";
        private MapsFragment frag;

        public LocationHandler(MapsFragment frag) {
            this.frag = frag;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "Location reçue : lat=" + location.getLatitude() + "/long=" + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            StringBuffer buf = new StringBuffer("Status modifié : dispositif="+s+"/status=");
            switch (i) {
                case LocationProvider.AVAILABLE:
                    buf.append("En service");
                    Integer t = (Integer)bundle.get("satellites");
                    if (t != null)
                        buf.append("satelittes="+t);
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    buf.append("Hors service");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    buf.append("Tenporairement indisponible");
                    break;
                default:
                    break;
            }
            Log.d(TAG, buf.toString());
        }

        @Override
        public void onProviderEnabled(String s) {

            Log.d(TAG, "Dispositif activé : "+s);
            String msg = String.format("The provider "+ s +" is now enabled");
            Toast.makeText(frag.getActivity(), msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {

            Log.d(TAG, "Dispositif désactivé : "+s);
            String msg = String.format("The provider "+ s +" is now disabled");
            Toast.makeText(frag.getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
