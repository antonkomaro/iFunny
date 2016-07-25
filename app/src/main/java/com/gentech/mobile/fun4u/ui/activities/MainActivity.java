package com.gentech.mobile.fun4u.ui.activities;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.adapters.ContentAdapter;
import com.gentech.mobile.fun4u.interfaces.UpdateListener;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.presenters.ContentPresenter;

import com.gentech.mobile.fun4u.ui.fragments.AdFragment;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements UpdateListener, ViewPager.OnPageChangeListener{
    public static final String TAG = MainActivity.class.getSimpleName();

//    private EventBus eventBus = EventBus.getDefault();

    private ContentPresenter presenter;

    private ContentAdapter adapter;
    private ArrayList<Content> contentItems;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onDestroy() {
//        eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
//        eventBus.register(this);

        this.presenter = new ContentPresenter(this, this);

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
            List<Fragment> fragments = presenter.buildFragments(contentItems);

            List<Fragment> checkedFragments = new ArrayList<>();
            for (Fragment fragment : fragments) {
                if (fragment instanceof AdFragment) {
                    AdFragment frag = (AdFragment) fragment;
                    boolean isAdLoaded = frag.isAdLoaded();
                    Log.d(TAG, "ccf ccf isAdLoaded " + isAdLoaded);
                    if (isAdLoaded) {
                        checkedFragments.add(fragment);
                    }
                } else {
                    checkedFragments.add(fragment);
                }
            }
            Log.d(TAG, "ccf checkedFragments " + checkedFragments.size());
            Log.d(TAG, "ccf fragments " + fragments.size());


            for (Fragment cf : checkedFragments) {
                Log.d(TAG, "cf is adfragment? " + (cf instanceof AdFragment));
            }

            adapter.addAll(checkedFragments);
        }
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
        presenter.postView(String.valueOf(adapter.getItem(position).getId()));
        int count = adapter.getCount();
        if (position == count - 1) {
            presenter.loadData(count);
        }
    }


}
