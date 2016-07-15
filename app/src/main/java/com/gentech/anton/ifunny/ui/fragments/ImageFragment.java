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

import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */
public class ImageFragment extends Fragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    public static Fragment newInstance(String contentUrl, String contentTitle) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.CONTENT_URL, contentUrl);
        bundle.putString(Config.CONTENT_TITLE, contentTitle);
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
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, rootView);
        loadContent();
        return rootView;
    }
    protected void loadContent() {
        ivContent.setImageURI(Uri.parse(getArguments().getString(Config.CONTENT_URL,"")));
        tvContent.setText(getArguments().getString(Config.CONTENT_TITLE,""));
    }
}
