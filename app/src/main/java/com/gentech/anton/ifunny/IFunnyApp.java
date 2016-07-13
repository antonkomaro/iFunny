package com.gentech.anton.ifunny;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by anton on 13.07.16.
 */
public class IFunnyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
