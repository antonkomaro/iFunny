package com.gentech.anton.ifunny.rest;

import com.gentech.anton.ifunny.rest.model.BaseModel;
import com.gentech.anton.ifunny.rest.model.TokenModel;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by anton on 12.07.16.
 */
public interface RestService {
    String SERVICE_ENDPOINT = "http://api.demo-stage.info/";

    @GET("posts/8")
    Observable<List<BaseModel>> loadData(@Query("offset") int offset, @Query("limit") int limit);

    @GET("registration")
    Observable<TokenModel> getAccessToken(@Query("User-agent") String userAgent);

    @GET("counters/set")
    Observable<ResponseBody> postLike(@Query("marker") String marker);

    @GET("counters/get")
    Observable<ResponseBody> getLikes(@Query("marker") String marker);
}

