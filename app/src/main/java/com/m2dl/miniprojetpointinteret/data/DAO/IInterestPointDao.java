package com.m2dl.miniprojetpointinteret.data.DAO;

import com.m2dl.miniprojetpointinteret.data.model.InterestPoint;

import java.util.List;

/**
 * Created by Romain on 21/01/2016.
 */
public interface IInterestPointDao {

    List<InterestPoint> findAll();

    boolean store(InterestPoint interestPoint);

    boolean store(List<InterestPoint> interestPoints);

    boolean delete(InterestPoint interestPoint);
}
