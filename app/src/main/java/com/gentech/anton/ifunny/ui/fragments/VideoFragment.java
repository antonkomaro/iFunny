package com.gentech.anton.ifunny.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.utils.Constants;
import com.gentech.anton.ifunny.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    public static final String TAG = VideoFragment.class.getSimpleName();
    private YouTubePlayer player;

    public static VideoFragment newInstance(String contentUrl, String contentTitle) {
        Log.d(TAG, "newInstance");
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contentUrl", contentUrl);
        bundle.putString("contentTitle", contentTitle);
        videoFragment.setArguments(bundle);

        return videoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        View v = layoutInflater.inflate(R.layout.fragment_video, viewGroup, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.youtube_fragment, youTubePlayerFragment)
                .commit();
        return v;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        Log.d(TAG, "onInitializationSuccess");
        final String videoId = Utils.getYoutubeVideoIdFromUrl(getArguments().getString("contentUrl"));
        Log.d(TAG, "videoId " + videoId);

        this.player = youTubePlayer;
        if (!restored) {
            player.cueVideo(videoId);
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
}
