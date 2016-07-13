package com.gentech.anton.ifunny.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
public class ContentAdapter extends FragmentPagerAdapter {
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

    public static int getPos() {
        return pos;
    }

//    public void add(Fragment fragment) {
//        fragments.add(fragment);
//        notifyDataSetChanged();
//    }

    public void add(List<Fragment> elements) {
        fragments.addAll(elements);
        notifyDataSetChanged();
    }

    public static void setPos(int pos) {
        ContentAdapter.pos = pos;
    }

//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        View rootView = inflater.inflate(R.layout.item_content, container, false);
////        ButterKnife.bind(this, rootView);
//
//        loadContent(position);
//        return rootView;
//    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }

//    private void loadContent(int position) {
//        ContentModel content = data.get(position);
//        if (content == null) {
//            Log.e(TAG, context.getString(R.string.no_content_found));
//            return;
//        }
//
//        switch (content.getContentType()) {
//            case IMAGE:
//                showImageFragment(content);
//                break;
//            case GIF:
//                showGifFragment(content);
//                break;
//            case VIDEO:
//                showVideoFragment(content);
//                break;
//            default:
//                Log.e(TAG, context
//                        .getString(R.string.unknown_content_type));
//                return;
//        }
//    }

//    private void showVideoFragment(ContentModel content) {
////        String videoId = Utils.getYoutubeVideoIdFromUrl(content.getUrl());
//
//        VideoFragment videoFragment = VideoFragment.newInstance("RvIK7Ch4kGE");
//        videoFragment.init();
//        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, videoFragment).commit();
//    }

//    private void showGifFragment(ContentModel content) {
//        GifFragment gifFragment = GifFragment
//                .newInstance(content.getUrl(), content.getTitle());
//        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, gifFragment).commit();
//    }
//
//    private void showImageFragment(ContentModel content) {
//        ImageFragment imageFragment = ImageFragment
//                .newInstance(content.getUrl(), content.getTitle());
//        ((MainActivity) context).getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, imageFragment).commit();
//    }

}
