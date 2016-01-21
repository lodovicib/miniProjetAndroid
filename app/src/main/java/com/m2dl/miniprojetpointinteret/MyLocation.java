package com.m2dl.miniprojetpointinteret;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.m2dl.miniprojetpointinteret.Fragments.AddFragment;
import com.m2dl.miniprojetpointinteret.Fragments.MapsFragment;


/**
 * Created by lgaleron on 10/01/2016.
 */
public class MyLocation {
    LocationListener loc;
    Location location;
    AddFragment frag;
    public static final String TAG = "LocationOverlay";

    public MyLocation(Location _location, AddFragment g) {
        frag = g;
        location = _location;
        loc = new LocationListener() {
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
                //Toast.makeText(mMapActivity, buf, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String s) {

                Log.d(TAG, "Dispositif activé : "+s);
                String msg = String.format("The provider"+ s +"is now enabled");
                Toast.makeText(frag.getActivity(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {

                Log.d(TAG, "Dispositif désactivé : "+s);
                String msg = String.format("The provider"+ s +"is now disabled");
                Toast.makeText(frag.getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public Location getLocation () {
        return location;
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }
    public boolean enableMyLocation() {
        return true;
    }

    public boolean sameLocation(Location _location) {
        if (location.getLatitude() == _location.getLatitude()) {
            if (location.getLongitude() == _location.getLongitude())
                return true;
        }
        return false;
    }

    public LocationListener getLocationListener() {
        return loc;
    }
}
