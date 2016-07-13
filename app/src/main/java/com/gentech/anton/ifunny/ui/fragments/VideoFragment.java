package com.gentech.anton.ifunny.ui.fragments;

import android.os.Bundle;
import android.util.Log;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.utils.Constants;
import com.gentech.anton.ifunny.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends YouTubePlayerSupportFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

//    private String currentVideoID = "video_id";
    private YouTubePlayer activePlayer;

    public static VideoFragment newInstance(String videoId) {

        VideoFragment videoFragment = new VideoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("videoId", videoId);

        videoFragment.setArguments(bundle);
        return videoFragment;
    }

    public void init() {

        initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
                if (result.toString().equals(getContext().getString(R.string.service_missing))) {
                    Utils.showSnackMessage(getView(), getContext().getString(R.string.player_error));
                }

                Log.e(TAG, getContext().getString(R.string.player_error));
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {
                    activePlayer.loadVideo(getArguments().getString("videoId"), 0);

                }
            }
        });
    }




}
