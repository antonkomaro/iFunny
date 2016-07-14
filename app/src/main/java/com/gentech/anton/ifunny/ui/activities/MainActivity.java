package com.gentech.anton.ifunny.ui.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.adapters.ContentAdapter;
import com.gentech.anton.ifunny.ui.fragments.GifFragment;
import com.gentech.anton.ifunny.ui.fragments.ImageFragment;
import com.gentech.anton.ifunny.ui.fragments.VideoThumbFragment;
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
    private ContentAdapter contentAdapter;

    @Bind(R.id.frame)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        contentAdapter = new ContentAdapter(getSupportFragmentManager());
        mPager.setAdapter(contentAdapter);
//        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Fragment fragment = contentAdapter.getItem(position);
//                Log.d(TAG, "fragment instanceof VideoFragment " + (fragment instanceof VideoFragment));
//                if (fragment instanceof VideoFragment) {
//                    ((VideoFragment) fragment).playVideo();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
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
                        updateAdapter(baseModels);
                    }
                });
    }

    private void updateAdapter(List<BaseModel> baseModel) {
        List<ContentModel> data = parseData(baseModel);
        List<Fragment> fragments = buildFragments(data);
        contentAdapter.add(fragments);
    }

    @NonNull
    private List<ContentModel> parseData(List<BaseModel> baseModel) {
        List<ContentModel> data = new ArrayList<>();
        ContentModel contentModel;
        for (BaseModel bm : baseModel) {
            Log.d(TAG, bm.toString());
            if (!bm.videos.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, (String) bm.videos.get(0), bm.views, bm.countComment, ContentType.VIDEO);
            } else if (bm.images.isEmpty()) {
                contentModel = new ContentModel(bm.id, bm.title, bm.img, bm.views, bm.countComment, ContentType.GIF);
            } else {
                contentModel = new ContentModel(bm.id, bm.title, bm.images.get(0), bm.views, bm.countComment, ContentType.IMAGE);
            }
            data.add(contentModel);
        }
        return data;
    }

    private List<Fragment> buildFragments(List<ContentModel> data) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("position", i);

            Fragment fragment = null;
            ContentModel contentModel = data.get(i);
            ContentType contentType = contentModel.getContentType();
            switch (contentType) {
                case IMAGE:
                    fragment = ImageFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
                case GIF:
                    fragment = GifFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
                case VIDEO:
                    fragment = VideoThumbFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
            }
            fragments.add(fragment);
        }
        return fragments;
    }
}
