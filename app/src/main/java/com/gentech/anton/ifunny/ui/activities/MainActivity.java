package com.gentech.anton.ifunny.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.adapters.ContentAdapter;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.rest.model.TokenModel;
import com.gentech.anton.ifunny.ui.fragments.AdFragment;
import com.gentech.anton.ifunny.ui.fragments.GifFragment;
import com.gentech.anton.ifunny.ui.fragments.ImageFragment;
import com.gentech.anton.ifunny.ui.fragments.VideoFragment;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.ContentType;
import com.gentech.anton.ifunny.rest.RestService;
import com.gentech.anton.ifunny.rest.ServiceFactory;
import com.gentech.anton.ifunny.rest.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.gentech.anton.ifunny.utils.ContentType.IMAGE;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private RestService service;
    private String regToken;

    private ContentAdapter adapter;
    private ArrayList<Content> contentItems;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_likes)
    TextView tvLikes;

    @Bind(R.id.btn_share)
    ImageButton btnShare;

    @Bind(R.id.btn_like)
    ImageButton btnLike;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        adapter = new ContentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(this);

        if (bundle == null || contentItems == null) {
            loadData();
        } else {
            contentItems = bundle.getParcelableArrayList(Config.CONTENT_ITEMS);
            updateAdapter();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d(TAG, "cc onSaveInstanceState contentItems " + contentItems);
        if (contentItems != null && !contentItems.isEmpty()) {
            bundle.putParcelableArrayList(Config.CONTENT_ITEMS, contentItems);
        }
    }

    private void updateAdapter() {
        Log.d(TAG, "cc updateAdapter");
        if (contentItems != null && !contentItems.isEmpty()) {
            Log.d(TAG, "cc updateAdapter in if");
            List<Fragment> fragments = buildFragments(contentItems);
            Log.d(TAG, "cc updateAdapter in if fragments " + fragments);
            adapter.addAll(fragments);
        }
    }

    // TODO: 15.07.16 Put this logic to presenter
    private void loadData() {
        service = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
        int limit = Config.LIMIT;
        int itemsCount = adapter.getCount();
        int offset = itemsCount == 0 ? Config.OFFSET : itemsCount;
        Log.d(TAG, "cc loadData itemsCount " + itemsCount);
        Log.d(TAG, "cc loadData offset " + offset);
        Log.d(TAG, "cc loadData limit " + limit);
        service.loadData(offset, limit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getLocalizedMessage()))
                .subscribe(baseModels -> {
                    if (baseModels != null && !baseModels.isEmpty()) {
                        contentItems = (ArrayList<Content>) parseData(baseModels);
                        updateAdapter();
                    }
                });

        getRegToken();
    }

    private void getRegToken() {
        service.getAccessToken("8")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {
                    regToken = accessToken.accessToken;
                    Log.d(TAG, "accessToken " + accessToken);
                });
    }

    @NonNull
    private List<Content> parseData(List<BaseModel> baseModel) {
        List<Content> data = new ArrayList<>();
        Content content;
        for (BaseModel bm : baseModel) {
            Log.d(TAG, bm.toString());
            if (!bm.videos.isEmpty()) {
                content = new Content(bm.id, bm.title, (String) bm.videos.get(0), bm.views, bm.countComment, ContentType.VIDEO);
            } else if (bm.images.isEmpty()) {
                content = new Content(bm.id, bm.title, bm.img, bm.views, bm.countComment, ContentType.GIF);
            } else {
                content = new Content(bm.id, bm.title, bm.images.get(0), bm.views, bm.countComment, IMAGE);
            }
            data.add(content);
        }
        return data;
    }

    private List<Fragment> buildFragments(List<Content> data) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {

            if (i % Config.AD_FREQUENCY == 0) {
                Fragment fragment = new AdFragment();
                fragments.add(fragment);
            }

            Fragment fragment;
            Content content = data.get(i);
            int contentType = content.getContentType();
            switch (contentType) {
                case ContentType.IMAGE:
                    fragment = ImageFragment.newInstance(content);
                    break;
                case ContentType.GIF:
                    fragment = GifFragment.newInstance(content);
                    break;
                case ContentType.VIDEO:
                    fragment = VideoFragment.newInstance(content);
                    break;
                default:
                    return null;
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
        if (position == adapter.getCount() - 1) {
            loadData();
        }
    }

    public void showLikes(int likes) {
        tvLikes.setText(String.valueOf(likes));
    }

    public void setupShare(String contentUrl) {
        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, contentUrl);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,
                    getResources().getText(R.string.send_to)));
        });
    }

    public void setupLike(String contentId) {
        btnLike.setOnClickListener(view -> postLike(contentId));
    }

    // TODO: 16.07.16 Not posting likes (which are comments in demo)
    public void postLike(String contentId) {
//        service.postLike(regToken, Config.LIKE_MSG, contentId)
//                .doOnError(t
//                        -> Log.e(TAG, "Error posting like " + t.getLocalizedMessage()))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(likeAddModel -> {
//                    Log.d(TAG, "likeAddModel " + likeAddModel);
        incrementLikes();
//                });

    }

    private void incrementLikes() {
        int currentLikes = (Integer.parseInt(tvLikes.getText().toString()));
        currentLikes++;
        tvLikes.setText(String.valueOf(currentLikes));
    }


}
