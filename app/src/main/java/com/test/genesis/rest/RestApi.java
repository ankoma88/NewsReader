package com.test.genesis.rest;

import com.test.genesis.rest.model.BaseModel;

import retrofit.Call;
import retrofit.http.GET;


/**
 * Created by ankoma88 on 18.06.16.
 */
public interface RestApi {
    @GET("blog_entries.json")
    Call<BaseModel> loadData();
}

