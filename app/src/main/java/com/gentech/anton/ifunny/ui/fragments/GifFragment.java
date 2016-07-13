package com.gentech.anton.ifunny.ui.fragments;

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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */

public class GifFragment extends Fragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    @Bind(R.id.tv_content)
    TextView tvContent;

    public static GifFragment newInstance(String contentUrl, String contentTitle) {

        GifFragment gifFragment = new GifFragment();

        Bundle bundle = new Bundle();
        bundle.putString("contentUrl", contentUrl);
        bundle.putString("contentTitle", contentTitle);

        gifFragment.setArguments(bundle);
        return gifFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, rootView);
        loadContent();
        return rootView;
    }

    private void loadContent() {
        tvContent.setText(getArguments().getString("contentTitle",""));
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(getArguments().getString("contentUrl","")))
                .setAutoPlayAnimations(true)
                .build();
        ivContent.setController(controller);
    }
}
