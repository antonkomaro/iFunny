package com.gentech.anton.ifunny.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.ContentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 12.07.16.
 */
public class CustomPagerAdapter extends PagerAdapter {
    public static final String TAG = CustomPagerAdapter.class.getSimpleName();

    Context context;
    //    private int pos = 0;
    private List<ContentModel> data = new ArrayList<>();

     @Bind(R.id.iv_gallery_item)
    ImageView ivContent;

    @Bind(R.id.tvContent)
    TextView tvContent;

    public CustomPagerAdapter(Context context, List<ContentModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ivContent = (ImageView) container.findViewById(R.id.iv_gallery_item);

        View rootView = inflater.inflate(R.layout.item_content, container, false);
        ButterKnife.bind(this, rootView);


        createImage(position, container, rootView);

        return rootView;


//        ImageView view = new ImageView(context);

//        LayoutInflater inflater = LayoutInflater.from(context);
//        View layout = inflater.inflate(R.layout.layout_content, container, false);
//        TextView view = (TextView) layout.findViewById(R.id.tvContent);
//        view.setText("1->" + position + " " + data.get(position).getUrl());
//
//        container.removeAllViews();
//        container.addView(view, 0);

//        view.setScaleType(ImageView.ScaleType.FIT_XY);

//        ContentModel contentModel = data.get(position);
//        Log.d(TAG, "cm " + contentModel);
//        Log.d(TAG, "instantiateItem " + Uri.parse(data.get(position).getUrl()));

//        view.setImageURI(Uri.parse(data.get(position).getUrl()));

//        if (pos >= data.size() -1) {
//            pos = 0;
//        } else {
//            ++pos;
//        }

//        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private void createImage(int position, ViewGroup container, View rootView) {
        String url = data.get(position).getUrl();
        ivContent.setImageURI(Uri.parse(url));
        tvContent.setText(data.get(position).getTitle());
        container.addView(rootView);

    }

}
