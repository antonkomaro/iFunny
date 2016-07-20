package com.gentech.anton.ifunny.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 12.07.16.
 */
public class ContentAdapter extends FragmentPagerAdapter {
    public static final String TAG = ContentAdapter.class.getSimpleName();

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public ContentAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
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
        Log.d(TAG, "cc elements " + elements);
        fragments.addAll(elements);
        notifyDataSetChanged();
        Log.d(TAG, "cc addAll fragments size: " + (fragments.size()));
    }

    @Override
    public void destroyItem(android.view.ViewGroup aContainer, int aPosition, java.lang.Object aObject)
    {
        if (aPosition <= getCount() && aObject != null)
        {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove((Fragment) aObject);
            trans.commit();
        }
    }

}
