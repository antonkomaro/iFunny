package com.gentech.mobile.fun4u.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.utils.Config;

import butterknife.Bind;

/**
 * Created by anton on 13.07.16.
 */

public class GifFragment extends ContentFragment {
    public static final String TAG = GifFragment.class.getSimpleName();

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    public static Fragment newInstance(Content content) {
        GifFragment fragment = new GifFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Config.CONTENT, content);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void loadContent() {
        super.loadContent();
        tvContent.setText(getContent().getTitle());

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(getContent().getUrl()))
                .setAutoPlayAnimations(true)
                .build();
        ivContent.setController(controller);

        setupButtons();
    }

    protected View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_gif, viewGroup, false);
    }


}