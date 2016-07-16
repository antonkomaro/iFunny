package com.gentech.anton.ifunny.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.utils.Config;

import butterknife.ButterKnife;

/**
 * Created by anton on 16.07.16.
 */
public abstract class ContentFragment extends Fragment {
    public static final String TAG = ContentFragment.class.getSimpleName();

    public Content getContent() {
        return getArguments().getParcelable(Config.CONTENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflateRootView(inflater, container);
        ButterKnife.bind(this, rootView);
        loadContent();
        return rootView;
    }

    protected abstract View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    protected void setupButtons() {
        ((MainActivity)getActivity()).showLikes(getContent().getLikeCount());
        ((MainActivity) getActivity()).setupShare(getContent().getUrl());
        ((MainActivity) getActivity()).setupLike(getContent().getId());
    }

    protected abstract void loadContent();

}
