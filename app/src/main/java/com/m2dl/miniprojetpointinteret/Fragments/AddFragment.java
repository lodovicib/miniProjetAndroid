package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.m2dl.miniprojetpointinteret.R;

import java.io.File;

/**
 * Created by lgaleron on 11/01/2016.
 */
public class AddFragment extends Fragment implements View.OnClickListener, LocationListener {

    private View view;
    private Spinner spinner;
    private Location location;
    private LocationManager locationManager;
    private Criteria critere;

    private Uri imageUri;
    private ImageView imageView;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public AddFragment() {
        super();
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.add_point_interet, container, false);

        Button b = (Button) view.findViewById(R.id.buttonPhoto);
        Button bAdd = (Button) view.findViewById(R.id.buttonAddPoint);
        b.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        initLocation();
        return view;
    }

    public void initLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        if (ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }

    public void takePhoto(View view) {
        //Création d'un intent
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //Création du fichier image
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

        //On lance l'intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //Si l'activité était une prise de photo
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    AddFragment.this.getActivity().getContentResolver().notifyChange(selectedImage, null);
                    imageView = (ImageView) AddFragment.this.getActivity().findViewById(R.id.imageView2);
                    ContentResolver cr = AddFragment.this.getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Toast.makeText(AddFragment.this.getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPhoto:
                takePhoto(v);
                break;
            case R.id.buttonAddPoint:
                spinner = (Spinner) AddFragment.this.getActivity().findViewById(R.id.spinnerTag);
                String errorText = "";
                if (spinner == null || spinner.getSelectedItemPosition() == 0)
                    errorText += "- Sélectionner un tag\n";
                if (imageView == null)
                    errorText += "- Mettre une image du point d\'intéret";
                // TODO n'ajouter que si on est à la fac !
                if (!errorText.equals(""))
                    Toast.makeText(AddFragment.this.getActivity(), "Veuillez remplir les champs suivants :\n"+errorText, Toast.LENGTH_SHORT)
                            .show();
                else
                    addPoint();
                break;
        }
    }

    public void addPoint() {
        // TODO use interestPoint service to store the new point (createPoint())
        // MapsFragment will know the change with firebase ValueChangeEvent
        Fragment fragmentMap = new MapsFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", location.getLatitude());
        bundle.putDouble("longitude", location.getLongitude());
        bundle.putString("tag", spinner.getSelectedItem().toString());
        fragmentMap.setArguments(bundle);

        android.support.v4.app.FragmentManager fragmentManager =  AddFragment.this.getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragmentMap);
        transaction.addToBackStack(null);
        transaction.commit();
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
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
        Log.i("onResume", "onResumeMap");
    }

    public void abonnementGPS() {
        if (ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    public void desabonnementGPS() {
        if (ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
