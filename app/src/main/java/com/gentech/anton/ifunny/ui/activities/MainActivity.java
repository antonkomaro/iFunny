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
import com.gentech.anton.ifunny.ui.fragments.VideoFragment;
import com.gentech.anton.ifunny.utils.Config;
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

import static com.gentech.anton.ifunny.utils.ContentType.IMAGE;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ContentAdapter contentAdapter;

    private ArrayList<ContentModel> contentItems;

    @Bind(R.id.frame)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        contentAdapter = new ContentAdapter(getSupportFragmentManager());
        pager.setAdapter(contentAdapter);
        pager.setOffscreenPageLimit(0);
        pager.addOnPageChangeListener(this);

        if (savedInstanceState == null) {
            loadData();
        } else {
            contentItems = savedInstanceState.getParcelableArrayList(Config.CONTENT_ITEMS);
            updateAdapter();
//            pager.setCurrentItem(contentAdapter.getPos());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Config.CONTENT_ITEMS, contentItems);
//        outState.putInt(Config.POSITION,  contentAdapter.getPos());
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
                        contentItems = (ArrayList<ContentModel>) parseData(baseModels);
                        updateAdapter();
                    }
                });
    }

    private void updateAdapter() {
        List<Fragment> fragments = buildFragments(contentItems);
        contentAdapter.addAll(fragments);
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
                contentModel = new ContentModel(bm.id, bm.title, bm.images.get(0), bm.views, bm.countComment, IMAGE);
            }
            data.add(contentModel);
        }
        return data;
    }

    private List<Fragment> buildFragments(List<ContentModel> data) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Bundle b = new Bundle();
            b.putInt(Config.POSITION, i);

            Fragment fragment = null;
            ContentModel contentModel = data.get(i);
            int contentType = contentModel.getContentType();
            switch (contentType) {
                case ContentType.IMAGE:
                    fragment = ImageFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
                case ContentType.GIF:
                    fragment = GifFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
                case ContentType.VIDEO:
                    fragment = VideoFragment.newInstance(contentModel.getUrl(), contentModel.getTitle());
                    break;
            }
            fragments.add(fragment);
        }
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
//        this.pos = position;

//        contentAdapter.setPos(position);

//        Log.d(TAG, "cc turn page position: " +  contentAdapter.getPos());
//        Log.d(TAG, "cc turn page fragments: " + (contentAdapter.getCount() - 1));

        if (position == contentAdapter.getCount() - 1) {
            loadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrolled");
    }
}
