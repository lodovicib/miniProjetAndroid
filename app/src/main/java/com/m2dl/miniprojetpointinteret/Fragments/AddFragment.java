package com.m2dl.miniprojetpointinteret.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2dl.miniprojetpointinteret.R;

/**
 * Created by lgaleron on 11/01/2016.
 */
public class AddFragment extends Fragment {

    private View view;

    public AddFragment() {
        super();
        // Just to be an empty Bundle. You can use this later with getArguments().set...
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.add_point_interet, container, false);

        return view;
    }
}
