package com.test.genesis.rest.model;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class Resultset {
    public String pagesize;

    public String count;

    public String page;

    @Override
    public String toString() {
        return "Resultset [pagesize = " + pagesize + ", count = " + count + ", page = " + page + "]";
    }
}

