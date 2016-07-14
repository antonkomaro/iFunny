package com.gentech.anton.ifunny.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.ContentModel;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.ui.fragments.GifFragment;
import com.gentech.anton.ifunny.ui.fragments.ImageFragment;
import com.gentech.anton.ifunny.ui.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by anton on 12.07.16.
 */
public class ContentAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = ContentAdapter.class.getSimpleName();

    public static int pos = 0;
    private List<Fragment> fragments;

    public ContentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(List<Fragment> elements) {
        fragments.addAll(elements);
        notifyDataSetChanged();
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        ContentAdapter.pos = pos;
    }

}
