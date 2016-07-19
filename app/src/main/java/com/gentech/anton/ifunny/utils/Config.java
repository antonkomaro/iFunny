package com.gentech.anton.ifunny.utils;

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

//    String FB_NATIVE_AD_PLACEMENT_ID = "YOUR_PLACEMENT_ID";
//    String FB_AD_HASHED_ID = "695e3070f27abbdd36d735a8cc32467c";
    String FB_ADS_REAL_ID = "296203837397452_296207080730461";

    int AD_FREQUENCY = 5;


}
