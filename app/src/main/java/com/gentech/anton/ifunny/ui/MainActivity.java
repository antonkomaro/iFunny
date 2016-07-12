package com.gentech.anton.ifunny.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.adapters.CustomPagerAdapter;
import com.gentech.anton.ifunny.enums.ContentType;
import com.gentech.anton.ifunny.models.ContentModel;
import com.gentech.anton.ifunny.rest.RestClient;
import com.gentech.anton.ifunny.rest.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<List<BaseModel>> {
    public static final String TAG = MainActivity.class.getSimpleName();

    private PagerAdapter pagerAdapter;
    private RestClient restClient;

    @Bind(R.id.frame)
    ViewPager mPager;

//    private int[] mImageArray = {android.R.drawable.ic_input_add,
//            android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_dialog_map,
//            android.R.drawable.ic_dialog_dialer};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        restClient = new RestClient(this);
        loadData();
    }

    private void loadData() {
        restClient.loadData();
    }

    @Override
    public void onResponse(Response<List<BaseModel>> response, Retrofit retrofit) {
        List<BaseModel> baseModel = response.body();

        List<ContentModel> data = new ArrayList<>();
        ContentModel contentModel;
        for (BaseModel bm : baseModel) {
            Log.d(TAG, bm.toString());
            if (!bm.videos.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, bm.url, bm.views, bm.countComment, ContentType.VIDEO);
            } else if (!bm.images.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, bm.img, bm.views, bm.countComment, ContentType.GIF);
            } else {
                contentModel = new ContentModel(bm.id, bm.title, bm.images.get(0), bm.views, bm.countComment, ContentType.IMAGE);
            }
            Log.d(TAG, "contentModel " + contentModel);
            data.add(contentModel);
        }
        Log.d(TAG, "data size " + data.size());
        onDataLoaded(data);

    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, t.getLocalizedMessage());
    }

    private void onDataLoaded(List<ContentModel> data) {
        pagerAdapter = new CustomPagerAdapter(this, data);
        mPager.setAdapter(pagerAdapter);
    }

}
