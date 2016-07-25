package com.gentech.mobile.fun4u.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 12.07.16.
 */
public class ContentAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = ContentAdapter.class.getSimpleName();

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

    public void addAll(List<Fragment> elements) {
        fragments.addAll(elements);
        notifyDataSetChanged();
    }

}
