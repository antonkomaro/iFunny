package com.gentech.anton.ifunny.ui.activities;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.adapters.ContentAdapter;
import com.gentech.anton.ifunny.utils.ContentType;
import com.gentech.anton.ifunny.models.ContentModel;
import com.gentech.anton.ifunny.rest.RestService;
import com.gentech.anton.ifunny.rest.ServiceFactory;
import com.gentech.anton.ifunny.rest.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.frame)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        loadData();
    }

    private void loadData() {
        RestService service = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
        service.loadData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BaseModel>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "loadData:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<BaseModel> baseModels) {
                        setAdapter(baseModels);
                    }
                });
    }

    private void setAdapter(List<BaseModel> baseModel) {
        List<ContentModel> data = parseData(baseModel);
        final ContentAdapter contentAdapter = new ContentAdapter(this, data);
        mPager.setAdapter(contentAdapter);
    }

    @NonNull
    private List<ContentModel> parseData(List<BaseModel> baseModel) {
        List<ContentModel> data = new ArrayList<>();
        ContentModel contentModel;
        for (BaseModel bm : baseModel) {
            Log.d(TAG, bm.toString());
            if (!bm.videos.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, bm.url, bm.views, bm.countComment, ContentType.VIDEO);
            } else if (bm.images.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, bm.img, bm.views, bm.countComment, ContentType.GIF);
            } else {
                contentModel = new ContentModel(bm.id, bm.title, bm.images.get(0), bm.views, bm.countComment, ContentType.IMAGE);
            }
            data.add(contentModel);
        }
        return data;
    }

}
