package com.gentech.mobile.fun4u.utils;

/**
 * Created by anton on 13.07.16.
 */
public interface Config {
    String YOUTUBE_API_KEY = "AIzaSyDvsBqLwGEVANEIwGCazcwx-h8MedLPGTI";
    String YOUTUBE_PATTERN = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

    String CONTENT_ITEMS = "contentItems";

    int OFFSET = 0;
    int LIMIT = 10;

    String CONTENT = "contentModel";

    String FB_AD_HASHED_ID = "3592bda0acf7cbb08a24cbb31d4df594";

//    String FB_NATIVE_AD_DEBUG_ID = "296203837397452_299337940417375";
//    String FB_NATIVE_AD_RELEASE_ID = "296203837397452_296207080730461";

    String FB_NATIVE_AD_RELEASE_ID = "296203837397452_299337940417375";

    int AD_FREQUENCY = 5;

    int PORTAL_DEMO = 8;
    int PORTAL_PROD = 10;

    String SHARE_MSG = "Get fun with Fun4U App! Install for Free "
            + "https://play.google.com/store/apps/details?id=com.gentech.mobile.fun4u";


}
