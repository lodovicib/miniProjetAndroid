package com.m2dl.miniprojetpointinteret.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.m2dl.miniprojetpointinteret.Preferences;
import com.m2dl.miniprojetpointinteret.R;
import com.m2dl.miniprojetpointinteret.model.InterestPointService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private View view;
    private Spinner spinner;
    private Location location;
    private LocationManager locationManager;
    private Criteria critere;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation;
    private Bitmap bitmap;
    private static Double latitudeUPS = 43.560573;
    private static Double longitudeUPS = 1.468520;

    private Uri imageUri;
    private ImageView imageView;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private TextView textViewType;
    private Spinner spinnerTag;
    private CheckBox cRecy, cDeg, cFuite;
    private Double latitude, longitude;
    private RadioButton rPoint;
    private RadioButton rZone;
    private Preferences pref;
    private InterestPointService interestPointService;

    public AddFragment() {
        super();
        setArguments(new Bundle());
    }

    public void setPointService(InterestPointService interestPointService) {
        this.interestPointService = interestPointService;
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

        pref = new Preferences(this.getActivity());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        return view;
    }

    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void recupLocation() {
        try {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        } catch (Exception e) {
            Toast.makeText(AddFragment.this.getActivity(), "Impossible de vous géolocaliser\nVeuillez activer votre position pour ajouter un point d\'intérêt", Toast.LENGTH_SHORT)
                    .show();
            Log.e("Location", e.getMessage());
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    AddFragment.this.getActivity().getContentResolver().notifyChange(selectedImage, null);
                    imageView = (ImageView) view.findViewById(R.id.imageView2);
                    ContentResolver cr = AddFragment.this.getActivity().getContentResolver();
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

    public byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
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
                    Toast.makeText(AddFragment.this.getActivity(), "Veuillez remplir les champs suivants :\n" + errorText, Toast.LENGTH_SHORT)
                            .show();
                else {
                    addPoint();
                }
                break;
        }
    }

    public boolean tooFarawayUPS() {
        float[] results = {0};
        Location.distanceBetween(latitude, longitude, latitudeUPS, longitudeUPS, results);
        if (results[0] < 1750)
            return false;
        return true;
    }

    public List<String> getTitleTag() {
        List<String> list = new ArrayList<String>();
        if (cDeg.isChecked())
            list.add(cDeg.getText().toString());
        if (cFuite.isChecked())
            list.add(cFuite.getText().toString());
        if (cRecy.isChecked()) {
            if (spinner != null && spinner.getSelectedItemPosition() != 0)
                list.add(cRecy.getText().toString() + " : " + spinner.getSelectedItem().toString());
            else
                list.add(cRecy.getText().toString());
        }
        return list;
    }

    public String getStringTag() {
        String text = "";
        if (cDeg.isChecked())
            text += cDeg.getText() + "\n";
        if (cFuite.isChecked())
            text += cFuite.getText() + "\n";
        if (cRecy.isChecked()) {
            text += cRecy.getText();
            if (spinner != null && spinner.getSelectedItemPosition() != 0)
                text += " : " + spinner.getSelectedItem().toString();
        }
        return text;
    }

    public void addPoint() {
        Fragment fragmentMap = new MapsFragment();
        interestPointService.createPoint(longitude, latitude, 0, getByteFromBitmap(bitmap), pref.getLogin(), getTitleTag());
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("tag", getStringTag());
        if (rZone.isChecked()) {
            Spinner s = (Spinner) view.findViewById(R.id.spinnerDiam);
            bundle.putInt("sizeZone", Integer.parseInt(s.getSelectedItem().toString()));
        }
        fragmentMap.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = AddFragment.this.getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragmentMap);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause", "onPauseMap");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume", "onResumeMap");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
