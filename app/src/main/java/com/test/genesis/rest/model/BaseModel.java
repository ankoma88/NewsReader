package com.test.genesis.rest.model;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class BaseModel {
    public Results[] results;

    public Metadata metadata;

    @Override
    public String toString() {
        return "BaseModel [results = " + results + ", metadata = " + metadata + "]";
    }
}
