package com.gentech.anton.ifunny.rest;
import com.gentech.anton.ifunny.rest.model.BaseModel;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class RestClient {
    private static final String REST_BASE_URL = "http://api.demo-stage.info/";
    private RestApi restApi;
    private Callback<List<BaseModel>> restCallback;

    public RestClient(Callback<List<BaseModel>> restCallback) {
        this.restCallback = restCallback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public void loadData() {
        Call<List<BaseModel>> call = restApi.loadData();
        call.enqueue(restCallback);
    }
}
