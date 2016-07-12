package com.gentech.anton.ifunny.rest;

import com.gentech.anton.ifunny.rest.model.BaseModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;


/**
 * Created by anton on 12.07.16.
 */
public interface RestApi {
    @GET("posts?portal=8")
    Call<List<BaseModel>> loadData();
}

