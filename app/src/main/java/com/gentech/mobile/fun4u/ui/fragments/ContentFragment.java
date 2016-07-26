package com.gentech.mobile.fun4u.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.db.DBHelper;
import com.gentech.mobile.fun4u.db.Like;
import com.gentech.mobile.fun4u.db.LikeDAO;
import com.gentech.mobile.fun4u.interfaces.ActionsListener;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.presenters.AnalyticsPresenter;
import com.gentech.mobile.fun4u.presenters.ContentPresenter;
import com.gentech.mobile.fun4u.ui.activities.MainActivity;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.Utils;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 16.07.16.
 */
public abstract class ContentFragment extends Fragment implements ActionsListener {
    public static final String TAG = ContentFragment.class.getSimpleName();

    private ContentPresenter presenter;

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.tv_likes)
    TextView tvLikes;

    @Bind(R.id.btn_share)
    ImageButton btnShare;

    @Bind(R.id.btn_like)
    ImageButton btnLike;


    public Content getContent() {
        return getArguments().getParcelable(Config.CONTENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new ContentPresenter(this, getContext());
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

    protected void setupButtons() {
        presenter.showLikes(getContent().getId());
        setupShare(getContent().getUrl());
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.postLike(ContentFragment.this.getContent().getId());
            }
        });

        setupLikeBtn(getContext(), getContent().getId());

    }

    public void setupLikeBtn(Context context, String postId) {
        LikeDAO likeDAO = new LikeDAO(context);
        Like like = likeDAO.get(postId);
        Log.d(TAG, "like get " + like);

        boolean isLiked = false;
        if (like != null && like.getIsLiked() == 1) {
            isLiked = true;
        }

        if (isLiked) {
            btnLike.setClickable(false);
            btnLike.setPressed(true);
        } else {
            btnLike.setClickable(true);
            btnLike.setPressed(false);
        }




    }

    protected void loadContent() {
        if (!Utils.isOnline(getContext())) {
            Utils.askToTurnOnInternet(getContext());
        }
    }

    protected void setupShare(String contentUrl) {
        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, contentUrl);

            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,
                    getResources().getText(R.string.send_to)));

            AnalyticsPresenter.getInstance().sendAnalyticsEvent(TAG,
                    AnalyticsPresenter.CATEGORY_POST_ACTIONS, AnalyticsPresenter.ACTION_POST_SHARED);

//            App.forceTestCrash();
        });
    }

    @Override
    public void updateLikes(ResponseBody likesCount) {
        try {
            String likes = new String(likesCount.bytes());
            tvLikes.setText(String.valueOf(likes));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveLike(String postId) {
        LikeDAO likeDAO = new LikeDAO(getContext());
        Like like = new Like();
        like.setPostId(postId);
        like.setIsLiked(1);
        likeDAO.save(like);

        btnLike.setClickable(false);
        btnLike.setPressed(true);
    }

}
