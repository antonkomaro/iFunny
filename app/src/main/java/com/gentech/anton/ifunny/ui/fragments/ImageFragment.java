package com.gentech.anton.ifunny.ui.fragments;

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
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.presenters.ContentPresenter;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 13.07.16.
 */
public class ImageFragment extends ContentFragment {
    public static final String TAG = VideoFragment.class.getSimpleName();

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    public static Fragment newInstance(Content content) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Config.CONTENT, content);
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void loadContent() {
        super.loadContent();
        ivContent.setImageURI(Uri.parse(getContent().getUrl()));
        tvContent.setText(getContent().getTitle());

        setupButtons();
    }

    protected View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_image, viewGroup, false);
    }

}
