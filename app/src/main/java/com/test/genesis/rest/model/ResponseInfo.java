package com.test.genesis.rest.model;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class ResponseInfo {
    public String status;

    public String developerMessage;

    @Override
    public String toString() {
        return "ResponseInfo [status = " + status + ", developerMessage = " + developerMessage + "]";
    }
}