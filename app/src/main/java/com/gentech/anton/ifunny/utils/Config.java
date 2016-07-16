package com.gentech.anton.ifunny.utils;

/**
 * Created by anton on 13.07.16.
 */
public interface Config {
    String YOUTUBE_API_KEY = "AIzaSyDvsBqLwGEVANEIwGCazcwx-h8MedLPGTI";
    String YOUTUBE_PATTERN = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

    String CONTENT_ITEMS = "contentItems";
    String POSITION = "position";

    int OFFSET = 0;
    int LIMIT = 10;

    String LIKE_MSG = "like";
    String CONTENT = "contentModel";
    String LIKES_COUNT = "likesCount";

    String FB_NATIVE_AD_PLACEMENT_ID = "YOUR_PLACEMENT_ID";
    String FB_AD_HASHED_ID = "695e3070f27abbdd36d735a8cc32467c";

    int AD_FREQUENCY = 5;

    int API_USER_AGENT = 8;
}
