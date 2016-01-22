package com.m2dl.miniprojetpointinteret.model.DAO;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.m2dl.miniprojetpointinteret.model.InterestPoint;
import com.m2dl.miniprojetpointinteret.model.InterestPointListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romain on 21/01/2016.
 */
public class InterestPointDaoFirebase implements IInterestPointDao {

    private static final Firebase DATABASE = new Firebase("https://dazzling-heat-8823.firebaseio.com/points");
    private List<InterestPointListener> listeners;

    public InterestPointDaoFirebase() {
        listeners = new ArrayList<>();
        // TODO implement this event
        // this one is just to retreive data when apllication start
        DATABASE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        // this one is to push a new point
        DATABASE.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean store(InterestPoint interestPoint) {
        DATABASE.setValue(interestPoint);
        return true;
    }

    @Override
    public boolean delete(InterestPoint interestPoint) {
        return false;
    }

    @Override
    public void addListener(InterestPointListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InterestPointListener listener) {
        listeners.remove(listener);
    }

    private void fireValueAdded(List<InterestPoint> interestPoints) {
        for (InterestPointListener l : listeners) {
            l.onPointsCreated(interestPoints);
        }
    }
}
