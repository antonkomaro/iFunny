package com.gentech.mobile.fun4u.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.gentech.mobile.fun4u.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anton on 13.07.16.
 */
public class Utils {

    public static String getYoutubeVideoIdFromUrl(String url) {
        Pattern compiledPattern = Pattern.compile(Config.YOUTUBE_PATTERN);
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


    public static void showToastMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isOnline(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static void askToTurnOnInternet(Context context) {
        Activity activity = (Activity) context;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(context.getString(R.string.turn_on_wifi));
        builder.setPositiveButton(context.getString(android.R.string.yes),
                (dialogInterface, i) -> {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                });
        builder.setNegativeButton(context.getString(android.R.string.no),
                (dialogInterface, i) -> {
                    activity.finish();
                });
        builder.create().show();
    }

}
