package com.gentech.anton.ifunny.ui.fragments;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends Fragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.wv_video)
    WebView wvVideo;

    public static VideoFragment newInstance(String contentUrl, String contentTitle) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.CONTENT_URL, contentUrl);
        bundle.putString(Config.CONTENT_TITLE, contentTitle);
        videoFragment.setArguments(bundle);

        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_video, viewGroup, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadContent();
    }

    @Override
    public void onPause() {
        super.onPause();
        wvVideo.onPause();
        wvVideo.clearCache(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadContent() {
        tvContent.setText(getArguments().getString(Config.CONTENT_TITLE,""));

        final String contentUrl = getArguments().getString(Config.CONTENT_URL, "");
        wvVideo.getSettings().setJavaScriptEnabled(true);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<iframe class=\"youtube-player\" " +
                "style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" "
                + " id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + Utils.getYoutubeVideoIdFromUrl(contentUrl)
                + "?fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n";
        wvVideo.setWebChromeClient(new WebChromeClient());
        wvVideo.loadDataWithBaseURL("", html, mimeType, encoding, "");
    }


}
