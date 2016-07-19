//package com.gentech.anton.ifunny.rest;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.gentech.anton.ifunny.models.Content;
//import com.gentech.anton.ifunny.utils.Config;
//
//import java.util.ArrayList;
//
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by anton on 18.07.16.
// */
//public class RestClient {
//    public static final String TAG = RestClient.class.getSimpleName();
//
//    private Context context;
//    private static RestService restService;
//    private static RestClient instance;
//
//    public static RestClient getInstance() {
//        if (instance == null)
//            throw new UnsupportedOperationException("Class not initialized yet!");
//        return instance;
//    }
//
//    private RestClient(Context context) {
//        this.context = context;
//    }
//
//    public static void init(Context context) {
//        restService = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
//        instance = new RestClient(context);
//    }
//
//
//
//
//}
