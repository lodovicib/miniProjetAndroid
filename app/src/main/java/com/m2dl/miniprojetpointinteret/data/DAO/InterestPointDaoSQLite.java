package com.m2dl.miniprojetpointinteret.data.DAO;

import com.m2dl.miniprojetpointinteret.data.model.InterestPoint;

import java.util.List;

/**
 * Created by Romain on 21/01/2016.
 */
public class InterestPointDaoSQLite implements IInterestPointDao {

    @Override
    public List<InterestPoint> findAll() {
        return null;
    }

    @Override
    public boolean store(InterestPoint interestPoint) {
        return false;
    }

    @Override
    public boolean store(List<InterestPoint> interestPoints) {
        return false;
    }

    @Override
    public boolean delete(InterestPoint interestPoint) {
        return false;
    }
}