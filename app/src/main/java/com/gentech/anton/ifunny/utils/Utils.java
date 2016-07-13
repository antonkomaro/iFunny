package com.gentech.anton.ifunny.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anton on 13.07.16.
 */
public class Utils {

    public static String getYoutubeVideoIdFromUrl(String url) {
        Pattern compiledPattern = Pattern.compile(Constants.YOUTUBE_PATTERN);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return "-1";
    }

    public static void showSnackMessage(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        TextView tv = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(view.getContext().getResources().getColor(android.R.color.primary_text_dark));
        snackbar.show();
    }


}
