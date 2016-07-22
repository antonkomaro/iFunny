package com.gentech.mobile.fun4u.ui.fragments;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.rest.RestService;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.List;

import butterknife.Bind;

/**
 * Created by anton on 13.07.16.
 */
public class VideoFragment extends ContentFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.thumb)
    SimpleDraweeView thumbnailView;

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
        super.loadContent();
        tvContent.setText(getContent().getTitle());

        String videoId = Utils.getYoutubeVideoIdFromUrl(getContent().getUrl());
        String thumbUrl = RestService.YT_IMAGE_ENDPOINT + videoId +"/hqdefault.jpg";
        Log.d(TAG, "thumbUrl " + thumbUrl);
        thumbnailView.setImageURI(Uri.parse(thumbUrl));

        thumbnailView.setOnClickListener(view -> {
            Intent intent;
            intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                    Config.YOUTUBE_API_KEY, videoId, 0, true, true);

            if (intent != null) {
                if (canResolveIntent(intent)) {
                    startActivityForResult(intent, 111);
                } else {
                    YouTubeInitializationResult
                            .SERVICE_MISSING
                            .getErrorDialog(getActivity(), 222).show();
                }
            }
        });

        setupButtons();
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

}
