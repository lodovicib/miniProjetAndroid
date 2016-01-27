package com.m2dl.miniprojetpointinteret.model;

import java.util.List;

public interface InterestPointListener {

    void onPointsCreated(List<InterestPoint> interestPoints);

    void onReadPointError();
}
