package com.m2dl.miniprojetpointinteret.model.DAO;

import com.m2dl.miniprojetpointinteret.model.InterestPoint;
import com.m2dl.miniprojetpointinteret.model.InterestPointListener;

import java.util.List;

/**
 * Created by Romain on 21/01/2016.
 */
public interface IInterestPointDao {

    boolean store(InterestPoint interestPoint);

    boolean delete(InterestPoint interestPoint);

    void addListener(InterestPointListener listener);

    void removeListener(InterestPointListener listener);
}
