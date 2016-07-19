package com.gentech.anton.ifunny;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by anton on 13.07.16.
 */
public class IFunnyApp extends Application {
    public static final String TAG = IFunnyApp.class.getSimpleName();

    public static Tracker tracker;

    public static Tracker tracker() {
        return tracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initAnalytics();
    }

    private void initAnalytics() {
        initGoogleAnalytics();
    }

    private void initGoogleAnalytics() {
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);

        tracker = googleAnalytics.newTracker(R.xml.global_tracker);
        tracker.enableAdvertisingIdCollection(true);
    }
}
