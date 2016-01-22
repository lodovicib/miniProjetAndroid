package com.m2dl.miniprojetpointinteret.utils;

import java.util.UUID;

/**
 * Created by Romain on 15/01/2016.
 */
public class IdGenerator {

    public String getIdAsString() {
        return UUID.randomUUID().toString();
    }

    public UUID getUUID() {
        return UUID.randomUUID();
    }
}
