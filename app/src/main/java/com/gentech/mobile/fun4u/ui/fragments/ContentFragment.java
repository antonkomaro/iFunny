package com.gentech.mobile.fun4u.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 16.07.16.
 */
public abstract class ContentFragment extends Fragment {
    public static final String TAG = ContentFragment.class.getSimpleName();

    @Nullable
    @Bind(R.id.tv_content)
    TextView tvContent;

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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadContent();
    }

    protected abstract View inflateRootView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    protected void loadContent() {
        if (!Utils.isOnline(getContext())) {
            Utils.askToTurnOnInternet(getContext());
        }
    }

}
