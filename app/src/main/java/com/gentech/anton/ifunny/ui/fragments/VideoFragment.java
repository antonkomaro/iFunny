package com.gentech.anton.ifunny.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends ContentFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.wv_video)
    WebView wvVideo;

    public static VideoFragment newInstance(Content content) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Config.CONTENT, content);
        videoFragment.setArguments(bundle);

        return videoFragment;
    }

    protected View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_video, viewGroup, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        wvVideo.onPause();
        wvVideo.clearCache(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void loadContent() {
        tvContent.setText(getContent().getTitle());

        final String contentUrl = getContent().getUrl();
        wvVideo.getSettings().setJavaScriptEnabled(true);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<iframe class=\"youtube-player\" " +
                "style=\"border: 0; width: 100%; height: 100%; padding:0; margin:0\" "
                + " id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + Utils.getYoutubeVideoIdFromUrl(contentUrl)
                + "?fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n";
        wvVideo.setWebChromeClient(new WebChromeClient());
        wvVideo.loadDataWithBaseURL("", html, mimeType, encoding, "");

        setupButtons();
    }


}
