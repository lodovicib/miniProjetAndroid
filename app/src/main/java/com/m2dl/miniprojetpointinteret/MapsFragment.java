package com.m2dl.miniprojetpointinteret;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lgaleron on 10/01/2016.
 */
public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, LocationListener {

    private Bundle saved;
    private static GoogleMap mMap;
    private static Double latitude, longitude;
    private static View view;
    private MyLocation loc;
    LocationManager locationManager;

    public MapsFragment() {
        super();
        // Just to be an empty Bundle. You can use this later with getArguments().set...
        setArguments(new Bundle());
    }

    public void abonnementGPS() {
        //On s'abonne
        if (ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.e("je passe", loc.getLocationListener().toString());
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, loc.getLocationListener());
    }

    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        if (ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(loc.getLocationListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.activity_maps, container, false);

        latitude = 43.560573;
        longitude = 1.468520;
        //LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return view;
    }

    private Criteria critere;
    private String best, provider;

    public void init_location() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        best = locationManager.getBestProvider(critere, true);
        if (ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        loc = new MyLocation(locationManager.getLastKnownLocation(best), this);
        if (best.equals("gps"))
            provider = LocationManager.GPS_PROVIDER;
        else
            provider = LocationManager.NETWORK_PROVIDER;
    }

    private void setUpMap() {
        System.out.println("je passe ici setup");
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
        mMap.setOnMyLocationButtonClickListener(this);
        init_location();
        enableMyLocation();
    }
 //   public static Fragment mapFragment;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("je passe ici created");
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            //mapFragment = getChildFragmentManager().findFragmentById(R.id.map);
            //mapFragment.getMapAsync(this);
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        desabonnementGPS();
        Log.i("onPause", "onPauseMap");
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) MapsFragment.this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
        Log.i("onResume", "onResumeMap");
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
    ///        UPSMapActivity.mapFragment.beginTransaction()
      //              .remove(UPSMapActivity.mapFragment.findFragmentById(R.id.map)).commit();
       //     ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).
            mMap = null;
        }
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

    @Override
    public void onLocationChanged(final Location location) {
        //On affiche dans un Toat la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());
        System.out.println(msg);
        Toast.makeText(MapsFragment.this.getActivity(), msg.toString(), Toast.LENGTH_SHORT).show();
    }

}
