package com.gentech.anton.ifunny.presenters;

import android.util.Log;

import com.gentech.anton.ifunny.App;
import com.google.android.gms.analytics.HitBuilders;

public class AnalyticsPresenter {
    private static final String TAG = AnalyticsPresenter.class.getSimpleName();

    public static final String SCREEN_NAME = "MainActivity";

    public static final String CATEGORY_POST_ACTIONS = "Post actions";
    public static final String CATEGORY_ADVERTISEMENT = "Advertisement";

    public static final String ACTION_POST_LIKED = "User liked a post";
    public static final String ACTION_POST_SHARED = "User shared a post";
    public static final String ACTION_FB_NATIVE_CLICKED = "FB native ad clicked";


    private static AnalyticsPresenter sInstance;

    private AnalyticsPresenter() {
    }

    public static AnalyticsPresenter getInstance() {
        if (sInstance == null) sInstance = new AnalyticsPresenter();
        return sInstance;
    }

    public void sendAnalyticsEvent(String screenName, String category, String action) {
        Log.d(TAG, "sendAnalyticsEvent " + action);
        sendGoogleAnalyticsEvent(screenName, category, action, "");
    }

    private void sendGoogleAnalyticsEvent(String screenName, String category, String action, String label) {
        App.tracker().setScreenName(screenName);
        App.tracker().send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build()
        );
    }

}
