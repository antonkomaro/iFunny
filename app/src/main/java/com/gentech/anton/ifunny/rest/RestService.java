package com.gentech.anton.ifunny.rest;

import com.gentech.anton.ifunny.rest.model.BaseModel;
import com.gentech.anton.ifunny.rest.model.TokenModel;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by anton on 12.07.16.
 */
public interface RestService {
    String SERVICE_ENDPOINT_DEMO = "http://api.demo-stage.info/";
    String SERVICE_ENDPOINT_PROD = "https://api.briefly.news/";

    String YT_IMAGE_ENDPOINT = "http://img.youtube.com/vi/";

    @GET("posts/{portal}")
    Observable<List<BaseModel>> loadData(@Path("portal") int portal, @Query("offset") int offset, @Query("limit") int limit);

//    @GET("registration")
//    Observable<TokenModel> getAccessToken(@Query("User-agent") String userAgent);

    @GET("counters/set")
    Observable<ResponseBody> postLike(@Query("marker") String marker);

    @GET("counters/get")
    Observable<ResponseBody> getLikes(@Query("marker") String marker);


}

