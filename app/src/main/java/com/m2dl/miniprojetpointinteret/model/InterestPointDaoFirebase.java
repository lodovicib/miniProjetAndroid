package com.m2dl.miniprojetpointinteret.model;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Romain on 21/01/2016.
 */
public class InterestPointDaoFirebase implements IInterestPointDao, ValueEventListener, ChildEventListener {

    private final Firebase database;
    private List<InterestPointListener> listeners;
    private int nbPointStart = 0;

    public InterestPointDaoFirebase(Firebase database) {
        listeners = new ArrayList<>();
        this.database = database.child("points");
        this.database.addListenerForSingleValueEvent(this);
    }

    @Override
    public boolean store(InterestPoint interestPoint) {
        database.child(interestPoint.getId()).setValue(interestPoint);
        return true;
    }

    @Override
    public boolean delete(InterestPoint interestPoint) {
        database.child(interestPoint.getId()).removeValue();
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

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
        if (nbPointStart > 0) {
            nbPointStart--;
            return;
        }
        InterestPoint newPoint = dataSnapshot.getValue(InterestPoint.class);
        if (newPoint != null)
            fireValueAdded(Arrays.asList(newPoint));
        Log.e("ChildAdded", newPoint.getId());
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
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<InterestPoint> interestPoints = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            InterestPoint point = snapshot.getValue(InterestPoint.class);
            if (point != null)
                interestPoints.add(point);
        }
        if (!interestPoints.isEmpty())
            fireValueAdded(interestPoints);
        for (InterestPoint point : interestPoints)
            Log.e("DataChanged", point.getId());
        nbPointStart = interestPoints.size();
        this.database.addChildEventListener(this);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        fireErrorOnRead();
        Log.e("FirebaseError", firebaseError.getMessage());
    }
}
