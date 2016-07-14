//package com.gentech.anton.ifunny.ui.fragments;
//
//import android.net.Uri;
//import android.os.Bundle;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.gentech.anton.ifunny.R;
//import com.gentech.anton.ifunny.utils.Constants;
//import com.gentech.anton.ifunny.utils.Utils;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerSupportFragment;
//import com.google.android.youtube.player.YouTubeThumbnailLoader;
//import com.google.android.youtube.player.YouTubeThumbnailView;
//
//import butterknife.Bind;
//
///**
// * Created by anton on 13.07.16.
// */
//public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
//    public static final String TAG = VideoFragment.class.getSimpleName();
//    private YouTubePlayer player;
//    private YouTubePlayerSupportFragment youTubePlayerFragment;
//
//    @Bind(R.id.youtube_fragment)
//    FrameLayout fl;
//
//    private String videoId;
//    private YouTubeThumbnailLoader thumbnailLoader;
//
//
//    public static VideoFragment newInstance(String contentUrl, String contentTitle) {
//        Log.d(TAG, "newInstance");
//        VideoFragment videoFragment = new VideoFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("contentUrl", contentUrl);
//        bundle.putString("contentTitle", contentTitle);
//        videoFragment.setArguments(bundle);
//
//        return videoFragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        Log.d(TAG, "onCreateView");
//        videoId = Utils.getYoutubeVideoIdFromUrl(getArguments().getString("contentUrl"));
//        View rootView = layoutInflater.inflate(R.layout.fragment_video, viewGroup, false);
//
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
//
//
////        fl.addView(ivThumb);
//
////        initYouTubePlayerFragment();
//
//        return rootView;
//    }
//
////    private void initYouTubePlayerFragment() {
////        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
////        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);
////        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////        fragmentManager.beginTransaction()
////                .replace(R.id.youtube_fragment, youTubePlayerFragment)
////                .commit();
////    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
//        Log.d(TAG, "onInitializationSuccess");
//        Log.d(TAG, "videoId " + videoId);
//
//        this.player = youTubePlayer;
//        if (!restored) {
//            player.cueVideo(videoId);
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
//        Log.d(TAG, "onInitializationFailure");
//        if (result.toString().equals(getContext().getString(R.string.service_missing))) {
//            Utils.showSnackMessage(getView(), getContext().getString(R.string.player_error));
//        }
//        Log.e(TAG, getContext().getString(R.string.player_error));
//    }
//
////    @Override
////    public void setUserVisibleHint(boolean isVisibleToUser) {
////        super.setUserVisibleHint(isVisibleToUser);
////
////        if (!isVisibleToUser && player != null) {
////            player.release();
////        }
////        if (isVisibleToUser && youTubePlayerFragment != null) {
////            youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);
////        }
////    }
//
////    public void playVideo() {
////        Log.d(TAG, "playVideo");
////        final String videoId = Utils.getYoutubeVideoIdFromUrl(getArguments().getString("contentUrl"));
////        Log.d(TAG, "videoId " + videoId);
////
////        boolean visible = youTubePlayerFragment.isVisible();
////        Log.d(TAG, "visible " + visible);
////
////        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);
////
////    }
//}
