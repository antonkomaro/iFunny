package com.gentech.anton.ifunny.ui.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ContentAdapter adapter;

    private ArrayList<ContentModel> contentItems;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle bundle) {
        Log.d(TAG, "cc onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        adapter = new ContentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(this);

        if (bundle == null) {
            loadData();
        } else {
            contentItems = bundle.getParcelableArrayList(Config.CONTENT_ITEMS);
            if (contentItems != null && !contentItems.isEmpty()) {
               updateAdapter();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d(TAG, "cc onSaveInstanceState contentItems " + contentItems);
        if (contentItems!=null && !contentItems.isEmpty()) {
            bundle.putParcelableArrayList(Config.CONTENT_ITEMS, contentItems);
        }
    }

    private void loadData() {
        RestService service = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
        int limit = Config.LIMIT;
        int itemsCount = adapter.getCount();
        int offset = itemsCount == 0 ? Config.OFFSET : itemsCount;
        Log.d(TAG, "cc loadData itemsCount " + itemsCount);
        Log.d(TAG, "cc loadData offset " + offset);
        Log.d(TAG, "cc loadData limit " + limit);
        service.loadData(offset, limit)
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
                        if (baseModels != null && !baseModels.isEmpty()) {
                            contentItems = (ArrayList<ContentModel>) parseData(baseModels);
                            updateAdapter();
                        }
                    }
                });
    }

    private void updateAdapter() {
        if (contentItems != null && !contentItems.isEmpty()) {
            List<Fragment> fragments = buildFragments(contentItems);
            adapter.addAll(fragments);
        }
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
        //do nothing
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        position = adapter.getItemPosition(currentFragment);
        if (position == adapter.getCount() - 1) {
            loadData();
        }
    }


}
