package com.gentech.anton.ifunny.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import butterknife.Bind;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends ContentFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

//   private YouTubePlayer player;

    @Bind(R.id.tv_content)
    TextView tvContent;

//    @Bind(R.id.wv_video)
//    WebView wvVideo;


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

//    @Override
//    public void onPause() {
//        super.onPause();
//        wvVideo.onPause();
//        wvVideo.clearCache(false);
//    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void loadContent() {
        tvContent.setText(getContent().getTitle());

        final String contentUrl = getContent().getUrl();

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
                Log.d(TAG, "onInitializationSuccess");
                final String videoId = Utils.getYoutubeVideoIdFromUrl(contentUrl);
                Log.d(TAG, "videoId " + videoId);

                if (!restored) {
                    youTubePlayer.cueVideo(videoId);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
                Log.d(TAG, "onInitializationFailure");
                if (result.toString().equals(getContext().getString(R.string.service_missing))) {
                    Utils.showSnackMessage(getView(), getContext().getString(R.string.player_error));
                }
                Log.e(TAG, getContext().getString(R.string.player_error));
            }
        });

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.youtube_fragment, youTubePlayerFragment)
                .commit();


//        wvVideo.getSettings().setJavaScriptEnabled(true);
//        final String mimeType = "text/html";
//        final String encoding = "UTF-8";
//        String html = "<iframe class=\"youtube-player\" " +
//                "style=\"border: 0; width: 100%; height: 100%; padding:0; margin:0\" "
//                + " id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
//                + Utils.getYoutubeVideoIdFromUrl(contentUrl)
//                + "?fs=0\" frameborder=\"0\">\n"
//                + "</iframe>\n";
//        wvVideo.setWebChromeClient(new WebChromeClient());
//        wvVideo.loadDataWithBaseURL("", html, mimeType, encoding, "");

        setupButtons();
    }


}
