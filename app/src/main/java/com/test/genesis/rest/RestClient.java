package com.test.genesis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.genesis.rest.model.BaseModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class RestClient {
    private static final String REST_BASE_URL = "https://www.justice.gov/api/v1/";
    private RestApi restApi;
    private Callback<BaseModel> restCallback;

    public RestClient(Callback<BaseModel> restCallback) {
        this.restCallback = restCallback;

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd")
//                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);

    }

    public void loadData() {
        Call<BaseModel> call = restApi.loadData();
        call.enqueue(restCallback);
    }
}
