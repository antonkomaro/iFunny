package com.gentech.mobile.fun4u;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;

/**
 * Created by anton on 13.07.16.
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();

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
        initCrashlytics();
    }

    private void initGoogleAnalytics() {
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);

        tracker = googleAnalytics.newTracker(R.xml.global_tracker);
        tracker.enableAdvertisingIdCollection(true);
    }

    private void initCrashlytics() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);
    }

    public static void forceTestCrash() {
        throw new RuntimeException("This is a crash");
    }
}
