package com.gentech.anton.ifunny.utils;

/**
 * Created by anton on 13.07.16.
 */
public interface Config {
    String YOUTUBE_API_KEY = "AIzaSyDvsBqLwGEVANEIwGCazcwx-h8MedLPGTI";
    String YOUTUBE_PATTERN = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

    String CONTENT_ITEMS = "contentItems";
    String POSITION = "position";

    String CONTENT_TITLE = "contentTitle";
    String CONTENT_URL = "contentUrl";

    int OFFSET = 0;
    int LIMIT = 10;
}
