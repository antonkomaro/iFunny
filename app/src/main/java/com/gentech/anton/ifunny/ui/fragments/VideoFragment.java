package com.gentech.anton.ifunny.ui.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import butterknife.Bind;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends ContentFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

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


    protected void loadContent() {
        tvContent.setText(getContent().getTitle());

        final String contentUrl = getContent().getUrl();

        YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) getChildFragmentManager()
                .findFragmentByTag("tag");

        fragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
                Log.d(TAG, "onInitializationSuccess");
                final String videoId = Utils.getYoutubeVideoIdFromUrl(contentUrl);
                Log.d(TAG, "videoId " + videoId);

                if (!restored) {
                    Log.d(TAG, "Not restored");
                    youTubePlayer.cueVideo(videoId);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
                Log.d(TAG, "onInitializationFailure " + result.toString());
                if (result.toString().equals(getContext().getString(R.string.service_missing))) {
                    Utils.showSnackMessage(getView(), getContext().getString(R.string.player_error));
                }
                Log.e(TAG, getContext().getString(R.string.player_error));
            }
        });

        setupButtons();
    }


}
