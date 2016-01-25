package com.m2dl.miniprojetpointinteret.model;

import com.m2dl.miniprojetpointinteret.model.DAO.IInterestPointDao;
import com.m2dl.miniprojetpointinteret.utils.IdGenerator;
import com.m2dl.miniprojetpointinteret.utils.PhotoDecoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Romain on 15/01/2016.
 */
public class InterestPointService implements InterestPointListener {

    private IInterestPointDao interestPointDao;
    private IdGenerator generator;
    private List<InterestPointListener> listeners;

    public InterestPointService(IInterestPointDao interestPointDao, IdGenerator generator) {
        this.interestPointDao = interestPointDao;
        this.generator = generator;
        listeners = new ArrayList<>();
    }

    public InterestPoint createPoint(double longitude,
                                     double latitude,
                                     double radius,
                                     byte[] photo,
                                     String userName,
                                     List<String> tags) {
        InterestPoint point = new InterestPoint();
        point.setId(generator.getIdAsString());
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        point.setPhoto(PhotoDecoder.byteToString(photo));
        point.setRadius(radius);
        point.setUserName(userName);
        point.setTags(tags);
        point.setDate(new Date().getTime());
        if (!interestPointDao.store(point)) {
            return null;
        }
        return point;
    }

    public boolean delete(InterestPoint interestPoint) {
        return interestPointDao.delete(interestPoint);
    }

    @Override
    public void onPointsCreated(List<InterestPoint> interestPoints) {
        fireValueAdded(interestPoints);
    }

    @Override
    public void onReadPointError() {
        fireErrorOnRead();
    }

    public void addListener(InterestPointListener listener) {
        listeners.add(listener);
    }

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
