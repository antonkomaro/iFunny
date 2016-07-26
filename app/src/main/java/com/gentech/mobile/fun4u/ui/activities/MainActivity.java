package com.gentech.mobile.fun4u.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.appevents.AppEventsLogger;
import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.adapters.ContentAdapter;
import com.gentech.mobile.fun4u.db.Like;
import com.gentech.mobile.fun4u.db.LikeDAO;
import com.gentech.mobile.fun4u.interfaces.ActionCallback;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.presenters.AnalyticsPresenter;
import com.gentech.mobile.fun4u.presenters.ContentPresenter;

import com.gentech.mobile.fun4u.ui.fragments.ContentFragment;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.Utils;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements ActionCallback, ViewPager.OnPageChangeListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ContentPresenter presenter;

    private ContentAdapter adapter;
    private ArrayList<Content> contentItems;

    private LikeDAO likeDAO;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.bottomPanel)
    LinearLayout llBottomPanel;

    @Bind(R.id.tv_likes)
    TextView tvLikes;

    @Bind(R.id.btn_share)
    ImageButton btnShare;

    @Bind(R.id.btn_like)
    ImageButton btnLike;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        this.presenter = new ContentPresenter(this, this);

        likeDAO = new LikeDAO(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        adapter = new ContentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(0);
        pager.addOnPageChangeListener(this);


        if (pager.getVisibility() == View.GONE) {
            pager.setVisibility(View.VISIBLE);
        }

        if (Utils.isOnline(this)) {
            if (bundle == null || contentItems == null) {
                loadData();

            } else {
                contentItems = bundle.getParcelableArrayList(Config.CONTENT_ITEMS);
                updateAdapter(contentItems);
            }

        } else {
            pager.setVisibility(View.GONE);
            Utils.askToTurnOnInternet(this);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (contentItems != null && !contentItems.isEmpty()) {
            bundle.putParcelableArrayList(Config.CONTENT_ITEMS, contentItems);
        }
    }

    @Override
    public void updateAdapter(List<Content> contentItems) {
        this.contentItems = (ArrayList<Content>) contentItems;
        if (contentItems != null && !contentItems.isEmpty()) {

            checkAdAndBuildFragments();
        }
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
        LikeDAO likeDAO = new LikeDAO(this);
        Like like = new Like();
        like.setPostId(postId);
        like.setIsLiked(1);
        likeDAO.save(like);

        btnLike.setClickable(false);
        btnLike.setPressed(true);
    }

    @Override
    public void showLikes(String contentId) {
        presenter.showLikes(contentId, this);
    }

    @Override
    public void postLike(String contentId) {
        presenter.postLike(contentId, this);
    }

    @Override
    public void setLikeListener(String contentId) {
        btnLike.setOnClickListener(view -> postLike(contentId));
    }

    private void checkAdAndBuildFragments() {
        AdSettings.addTestDevice(Config.FB_AD_HASHED_ID);
        NativeAd nativeAd = new NativeAd(this, Config.FB_PLACEMENT_ID);
        nativeAd.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                adapter.addAll(presenter.buildFragments(contentItems, false));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                adapter.addAll(presenter.buildFragments(contentItems, true));

//              Hack to switch like button for the first time
                pager.post(() -> onPageSelected(pager.getCurrentItem()));
            }

            @Override
            public void onAdClicked(Ad ad) {
                //do nothing
            }
        });
        nativeAd.loadAd();
    }

    private void loadData() {
        presenter.loadData(adapter.getCount());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //do nothing
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = adapter.getItem(position);
        presenter.postView(String.valueOf(fragment.getId()));

        if (fragment instanceof ContentFragment) {
            setupButtonsVisibility(true);
            setupButtons(((ContentFragment) fragment).getContent());
        } else {
            setupButtonsVisibility(false);
        }


        int count = adapter.getCount();
        if (position == count - 1) {
            presenter.loadData(count);
        }
    }

    private void setupButtonsVisibility(boolean visible) {
        llBottomPanel.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setupButtons(Content content) {
        showLikes(content.getId());
        setupShare(content.getUrl());
        setLikeListener(content.getId());
        setupLikeBtn(this, content.getId());
    }

    @Override
    public void setupLikeBtn(Context context, String postId) {
        Like like = likeDAO.get(postId);

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

    @Override
    public void setupShare(String contentUrl) {
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


}
