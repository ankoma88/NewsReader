package com.test.genesis.rest.model;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class Metadata {
    public String executionTime;

    public Resultset resultset;

    public ResponseInfo responseInfo;

    @Override
    public String toString() {
        return "Metadata [executionTime = " + executionTime + ", resultset = " + resultset
                + ", responseInfo = " + responseInfo + "]";
    }
}
