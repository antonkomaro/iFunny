package com.gentech.mobile.fun4u.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.utils.Config;

import butterknife.Bind;

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

        if (tvContent != null) {
            tvContent.setText(getContent().getTitle());
        }

    }

    protected View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_image, viewGroup, false);
    }

}
