package com.m2dl.miniprojetpointinteret.model.DAO;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.m2dl.miniprojetpointinteret.model.InterestPoint;
import com.m2dl.miniprojetpointinteret.model.InterestPointListener;

import java.util.ArrayList;
import java.util.Arrays;
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
                List<InterestPoint> interestPoints = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getValue(String.class);
                    InterestPoint point = snapshot.child(id).getValue(InterestPoint.class);
                    if (point != null)
                        interestPoints.add(point);
                }
                if (!interestPoints.isEmpty())
                    fireValueAdded(interestPoints);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                fireErrorOnRead();
                Log.e("FirebaseError", firebaseError.getMessage());
            }
        });
        // this one is to push a new point
        DATABASE.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                String id = dataSnapshot.getValue(String.class);
                InterestPoint newPoint = dataSnapshot.child(id).getValue(InterestPoint.class);
                if (newPoint != null)
                    fireValueAdded(Arrays.asList(newPoint));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                // not yet implemented
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // not yet implemented
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildKey) {
                // not yet implemented
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                fireErrorOnRead();
                Log.e("FirebaseError", firebaseError.getMessage());
            }
        });
    }

    @Override
    public boolean store(InterestPoint interestPoint) {
        DATABASE.child(interestPoint.getId()).setValue(interestPoint);
        return true;
    }

    @Override
    public boolean delete(InterestPoint interestPoint) {
        DATABASE.child(interestPoint.getId()).removeValue();
        return true;
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

    private void fireErrorOnRead() {
        for (InterestPointListener l : listeners) {
            l.onReadPointError();
        }
    }
}
