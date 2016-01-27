package com.m2dl.miniprojetpointinteret.utils;

import java.util.UUID;

public class IdGenerator {

    public String getIdAsString() {
        return UUID.randomUUID().toString();
    }

    public UUID getUUID() {
        return UUID.randomUUID();
    }
}
