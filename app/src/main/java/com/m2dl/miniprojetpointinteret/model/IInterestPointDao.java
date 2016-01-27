package com.m2dl.miniprojetpointinteret.model;

public interface IInterestPointDao {

    boolean store(InterestPoint interestPoint);

    boolean delete(InterestPoint interestPoint);

    void addListener(InterestPointListener listener);

    void removeListener(InterestPointListener listener);
}
