package com.gentech.anton.ifunny.ui.fragments;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */

public class GifFragment extends Fragment {
    public static final String TAG = GifFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    public static Fragment newInstance(String contentUrl, String contentTitle, int contentLikes) {
        GifFragment fragment = new GifFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.CONTENT_URL, contentUrl);
        bundle.putString(Config.CONTENT_TITLE, contentTitle);
        bundle.putInt(Config.CONTENT_LIKES, contentLikes);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gif, container, false);
        ButterKnife.bind(this, rootView);
        loadContent();
        return rootView;
    }

    private void loadContent() {
        tvContent.setText(getArguments().getString(Config.CONTENT_TITLE,""));
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(getArguments().getString(Config.CONTENT_URL,"")))
                .setAutoPlayAnimations(true)
                .build();
        ivContent.setController(controller);

        ((MainActivity)getActivity()).updateLikes(getArguments().getInt(Config.CONTENT_LIKES,0));
        ((MainActivity) getActivity()).setupShare(getArguments().getString(Config.CONTENT_URL,""));
    }



}
