package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;
import com.m2dl.miniprojetpointinteret.model.BindService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgaleron on 11/01/2016.
 */
public class AddFragment extends Fragment implements View.OnClickListener, LocationListener, CompoundButton.OnCheckedChangeListener {

    private View view;
    private Spinner spinner;
    private Location location;
    private LocationManager locationManager;
    private Criteria critere;

    private Uri imageUri;
    private ImageView imageView;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    TextView textViewType;
    Spinner spinnerTag;
    CheckBox cRecy, cDeg, cFuite;
    private Double latitude, longitude;
    RadioButton rPoint;
    RadioButton rZone;
    private BindService bindService;
    private Preferences pref;

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
        cRecy = (CheckBox) view.findViewById(R.id.checkBoxRecy);
        rPoint = (RadioButton) view.findViewById(R.id.radioButtonPoint);
        rZone = (RadioButton) view.findViewById(R.id.radioButtonZone);
        b.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        cRecy.setOnClickListener(this);
        rPoint.setOnCheckedChangeListener(this);
        rZone.setOnCheckedChangeListener(this);

        textViewType = (TextView) view.findViewById(R.id.textViewType);
        textViewType.setVisibility(View.INVISIBLE);
        spinnerTag = (Spinner) view.findViewById(R.id.spinnerTag);
        spinnerTag.setVisibility(View.INVISIBLE);

        initLocation();
        //recupLocation();
        bindService = BindService.getInstance();
        pref = new Preferences(this.getActivity());
        return view;
    }

    public void recupLocation() {
        try {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } catch (Exception e) {
            Toast.makeText(AddFragment.this.getActivity(), "Impossible de vous géolocaliser\nVeuillez activer votre position pour ajouter un point d\'intérêt", Toast.LENGTH_SHORT)
                    .show();
            Log.e("Location", e.getMessage());
        }
    }

    public void initLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        if (ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            // Or use LocationManager.GPS_PROVIDER
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
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
                    imageView = (ImageView) view.findViewById(R.id.imageView2);
                    ContentResolver cr = AddFragment.this.getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        imageView.setImageBitmap(rotatedBitmap);
                        recupLocation();
                    } catch (Exception e) {
                        Toast.makeText(AddFragment.this.getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radioButtonPoint) {
                rZone.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButtonZone) {
                rPoint.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkBoxRecy:
                if (cRecy.isChecked()) {
                    textViewType.setVisibility(View.VISIBLE);
                    spinnerTag.setVisibility(View.VISIBLE);
                } else {
                    textViewType.setVisibility(View.INVISIBLE);
                    spinnerTag.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.buttonPhoto:
                takePhoto(v);
                break;
            case R.id.buttonAddPoint:
                String errorText = "";
                cDeg = (CheckBox) view.findViewById(R.id.checkBoxDeg);
                cFuite = (CheckBox) view.findViewById(R.id.checkBoxFuite);

                if (!cDeg.isChecked() && !cFuite.isChecked() && !cRecy.isChecked())
                    errorText += "- Sélectionner au moins 1 tag\n";
                if (imageView == null)
                    errorText += "- Mettre une image du point d\'intéret";
                if (latitude == null) {
                    Toast.makeText(AddFragment.this.getActivity(), "Problème de géolocalisation", Toast.LENGTH_SHORT)
                            .show();
                    recupLocation();
                } else if (!errorText.equals(""))
                    Toast.makeText(AddFragment.this.getActivity(), "Veuillez remplir les champs suivants :\n"+errorText, Toast.LENGTH_SHORT)
                            .show();
                else {
                    addPoint();
                }
                // TODO n'ajouter que si on est à la fac !
                break;
        }
    }

    public List<String> getTitleTag() {
        List<String> list = new ArrayList<String>();
        if (cDeg.isChecked())
            list.add(cDeg.getText().toString());
        if (cFuite.isChecked())
            list.add(cFuite.getText().toString());
        if (cRecy.isChecked()) {
            if (spinner != null && spinner.getSelectedItemPosition() != 0)
                list.add(cRecy.getText().toString()+" : "+ spinner.getSelectedItem().toString());
            else
                list.add(cRecy.getText().toString());
        }
        return list;
    }

    public void addPoint() {
        // TODO use interestPoint service to store the new point (createPoint())
        // MapsFragment will know the change with firebase ValueChangeEvent
        Fragment fragmentMap = new MapsFragment();
        bindService.getInterestPointService().createPoint(longitude, latitude, 0, null, pref.getLogin(), getTitleTag());
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
       // bundle.putString("tag", getTitleTag());
        if (rZone.isChecked()) {
            Spinner s = (Spinner) view.findViewById(R.id.spinnerDiam);
            bundle.putInt("sizeZone", Integer.parseInt(s.getSelectedItem().toString()));
        }
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
