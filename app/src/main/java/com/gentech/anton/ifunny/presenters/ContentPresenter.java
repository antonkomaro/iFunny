package com.gentech.anton.ifunny.presenters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gentech.anton.ifunny.interfaces.ActionsListener;
import com.gentech.anton.ifunny.interfaces.UpdateListener;
import com.gentech.anton.ifunny.models.Content;
import com.gentech.anton.ifunny.rest.RestService;
import com.gentech.anton.ifunny.rest.ServiceFactory;
import com.gentech.anton.ifunny.rest.model.BaseModel;
import com.gentech.anton.ifunny.ui.fragments.AdFragment;
import com.gentech.anton.ifunny.ui.fragments.GifFragment;
import com.gentech.anton.ifunny.ui.fragments.ImageFragment;
import com.gentech.anton.ifunny.ui.fragments.VideoFragment;
import com.gentech.anton.ifunny.utils.Config;
import com.gentech.anton.ifunny.utils.ContentType;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gentech.anton.ifunny.utils.ContentType.IMAGE;

/**
 * Created by anton on 18.07.16.
 */
public class ContentPresenter {
    public static final String TAG = ContentPresenter.class.getSimpleName();

    private UpdateListener updateListener;
    private ActionsListener actionsListener;

//    private static RestClient restClient;
    private RestService service;
    private ArrayList<Content> contentItems;

    public ContentPresenter(ActionsListener actionsListener) {
//        restClient = RestClient.getInstance();
        this.actionsListener = actionsListener;
        service = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
    }

    public ContentPresenter(UpdateListener updateListener) {
//        restClient = RestClient.getInstance();
        this.updateListener = updateListener;
        service = ServiceFactory.createRestService(RestService.class, RestService.SERVICE_ENDPOINT);
    }

    public void loadData(int itemsCount) {
        int limit = Config.LIMIT;
//        int itemsCount = adapter.getCount();
        int offset = itemsCount == 0 ? Config.OFFSET : itemsCount;
        service.loadData(offset, limit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getLocalizedMessage()))
                .subscribe(baseModels -> {
                    if (baseModels != null && !baseModels.isEmpty()) {
                        contentItems = (ArrayList<Content>) parseData(baseModels);
                        updateListener.updateAdapter(contentItems);
                    }
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

    public List<Fragment> buildFragments(List<Content> data) {
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

    public void showLikes(String contentId) {
        String marker = "onPageSelected likes_post_id_" + contentId;
        service.getLikes(marker)
                .doOnError(t
                        -> Log.e(TAG, "Error getting likes count " + t.getLocalizedMessage()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((likesCount) -> {
                    actionsListener.updateLikes(likesCount);
                    Log.d(TAG, "onPageSelected likes showLikes contentId "
                            + contentId + " likes " + likesCount);
                });
    }

    public void postLike(String contentId) {
        String marker = "likes_post_id_" + contentId;
        service.postLike(marker)
                .doOnError(t -> {
                    Log.e(TAG, "Error posting like " + t.getLocalizedMessage());
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((likesCount) -> {
                    actionsListener.updateLikes(likesCount);
                    Log.d(TAG, "likes postLike contentId " + contentId + " likes " + likesCount);
                });
        AnalyticsPresenter.getInstance().sendAnalyticsEvent(
                TAG, AnalyticsPresenter.CATEGORY_POST_ACTIONS, AnalyticsPresenter.ACTION_POST_LIKED);
    }
}
