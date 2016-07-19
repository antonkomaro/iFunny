package com.gentech.anton.ifunny.ui.activities;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.adapters.ContentAdapter;
import com.gentech.anton.ifunny.interfaces.UpdateListener;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.presenters.ContentPresenter;

import com.gentech.anton.ifunny.utils.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements UpdateListener, ViewPager.OnPageChangeListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ContentPresenter presenter;

    private ContentAdapter adapter;
    private ArrayList<Content> contentItems;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        this.presenter = new ContentPresenter(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        adapter = new ContentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(0);
        pager.addOnPageChangeListener(this);

        if (bundle == null || contentItems == null) {
            loadData();
        } else {
            contentItems = bundle.getParcelableArrayList(Config.CONTENT_ITEMS);
            updateAdapter(contentItems);
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
            adapter.addAll(fragments);
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
        int count = adapter.getCount();
        if (position == count - 1) {
            presenter.loadData(count);
        }
    }
}
