package com.gentech.mobile.fun4u.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.interfaces.ActionsListener;
import com.gentech.mobile.fun4u.interfaces.UpdateListener;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.rest.RestService;
import com.gentech.mobile.fun4u.rest.ServiceFactory;
import com.gentech.mobile.fun4u.rest.model.BaseModel;
import com.gentech.mobile.fun4u.ui.fragments.AdFragment;
import com.gentech.mobile.fun4u.ui.fragments.GifFragment;
import com.gentech.mobile.fun4u.ui.fragments.ImageFragment;
import com.gentech.mobile.fun4u.ui.fragments.VideoFragment;
import com.gentech.mobile.fun4u.utils.Config;
import com.gentech.mobile.fun4u.utils.ContentType;
import com.gentech.mobile.fun4u.utils.Utils;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gentech.mobile.fun4u.utils.ContentType.IMAGE;

/**
 * Created by anton on 18.07.16.
 */
public class ContentPresenter {
    public static final String TAG = ContentPresenter.class.getSimpleName();
    private String endPoint;
    private int portal;

    private Context context;
    private UpdateListener updateListener;
    private ActionsListener actionsListener;

    private RestService service;
    private ArrayList<Content> contentItems;

    public ContentPresenter(ActionsListener actionsListener,  Context context) {
        this.actionsListener = actionsListener;
        this.context = context;
        setEndPoint(context);
        service = ServiceFactory.createRestService(RestService.class, endPoint);
    }

    public ContentPresenter(UpdateListener updateListener, Context context) {
        this.updateListener = updateListener;
        this.context = context;
        setEndPoint(context);
        service = ServiceFactory.createRestService(RestService.class, endPoint);
    }

    @NonNull
    private void setEndPoint(Context context) {

        if (context.getResources().getBoolean(R.bool.debug)) {
            endPoint = RestService.SERVICE_ENDPOINT_DEMO;
            portal = Config.PORTAL_DEMO;
        } else {
            endPoint = RestService.SERVICE_ENDPOINT_PROD;
            portal = Config.PORTAL_PROD;
        }
    }

    public void loadData(int itemsCount) {
        int limit = Config.LIMIT;
        int offset = itemsCount == 0 ? Config.OFFSET : itemsCount;

        service.loadData(portal, offset, limit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getLocalizedMessage()))
                .subscribe(new Subscriber<List<BaseModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadError(e);
                    }

                    @Override
                    public void onNext(List<BaseModel> baseModels) {
                        if (baseModels != null && !baseModels.isEmpty()) {
                            contentItems = (ArrayList<Content>) ContentPresenter.this.parseData(baseModels);
                            updateListener.updateAdapter(contentItems);
                        }
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

            if (i % Config.AD_FREQUENCY == 1) {
                Fragment fragment = AdFragment.newInstance();
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
        String marker = "likes_post_id_" + contentId;
        service.getLikes(marker)
                .doOnError(t
                        -> Log.e(TAG, "Error getting likes count " + t.getLocalizedMessage()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadError(e);
                    }

                    @Override
                    public void onNext(ResponseBody likesCount) {
                        actionsListener.updateLikes(likesCount);
                    }
                });
    }

    public void postLike(String contentId) {
        String marker = "likes_post_id_" + contentId;
        service.postLike(marker)
                .doOnError(t -> Log.e(TAG, "Error posting like " + t.getLocalizedMessage()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadError(e);
                    }

                    @Override
                    public void onNext(ResponseBody likesCount) {
                        actionsListener.updateLikes(likesCount);
                    }
                });
        AnalyticsPresenter.getInstance().sendAnalyticsEvent(
                TAG, AnalyticsPresenter.CATEGORY_POST_ACTIONS, AnalyticsPresenter.ACTION_POST_LIKED);
    }

    public void postView(String contentId) {
        String marker = "views_post_id_" + contentId;
        service.postView(marker)
                .doOnError(t -> Log.e(TAG, "Error posting view " + t.getLocalizedMessage()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadError(e);
                    }

                    @Override
                    public void onNext(ResponseBody views) {
                        Log.d(TAG, "Updated views counter of post " + contentId);
                    }
                });
        AnalyticsPresenter.getInstance().sendAnalyticsEvent(
                TAG, AnalyticsPresenter.CATEGORY_POST_ACTIONS, AnalyticsPresenter.ACTION_POST_LIKED);
    }

    private void onLoadError(Throwable e) {
        if (!Utils.isOnline(context)) {
            Utils.askToTurnOnInternet(context);
        }
    }

}
