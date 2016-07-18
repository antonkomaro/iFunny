package com.gentech.anton.ifunny.rest;

import com.gentech.anton.ifunny.rest.model.BaseModel;
import com.gentech.anton.ifunny.rest.model.LikeAddModel;
import com.gentech.anton.ifunny.rest.model.TokenModel;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @FormUrlEncoded
    @POST("comment/add")
    Observable<LikeAddModel> postLike(@Query("access-token") String accessToken,
                                     @Field("msg") String msg, @Field("post_id") String postId);
}

