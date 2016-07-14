package com.gentech.anton.ifunny.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.utils.Constants;
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
public class VideoThumbFragment extends Fragment implements YouTubeThumbnailView.OnInitializedListener{
    public static final String TAG = VideoThumbFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.youtube_thumb)
    YouTubeThumbnailView ivThumb;

    private String videoId;
    private YouTubeThumbnailLoader thumbnailLoader;


    public static VideoThumbFragment newInstance(String contentUrl, String contentTitle) {
        Log.d(TAG, "newInstance");
        VideoThumbFragment videoThumbFragment = new VideoThumbFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contentUrl", contentUrl);
        bundle.putString("contentTitle", contentTitle);
        videoThumbFragment.setArguments(bundle);

        return videoThumbFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        videoId = Utils.getYoutubeVideoIdFromUrl(getArguments().getString("contentUrl"));
        View rootView = layoutInflater.inflate(R.layout.fragment_video_thumb, viewGroup, false);

        tvContent.setText(getArguments().getString("contentTitle",""));

//        YouTubeThumbnailView ivThumb = new YouTubeThumbnailView(getContext());
//        ivThumb.setTag(videoId);
//        ivThumb.initialize(Constants.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                thumbnailLoader = youTubeThumbnailLoader;
//                thumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                    @Override
//                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                        Log.d(TAG, "onThumbnailLoaded");
//                    }
//
//                    @Override
//                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//                        Log.d(TAG, "onThumbnailError");
//                    }
//                });
//
//                youTubeThumbnailLoader.setVideo(ivThumb.getTag().toString());
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });

        return rootView;
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        thumbnailLoader = youTubeThumbnailLoader;
        thumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                Log.d(TAG, "onThumbnailLoaded");
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                Log.d(TAG, "onThumbnailError");
            }
        });

        youTubeThumbnailLoader.setVideo(ivThumb.getTag().toString());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        Log.e(TAG, "onInitializationFailure");
    }




}
