package com.gentech.mobile.fun4u.interfaces;

import android.content.Context;

import com.gentech.mobile.fun4u.models.Content;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

/**
 * Created by anton on 18.07.16.
 */
public interface ActionCallback {
    void updateAdapter(List<Content> contentList);

    void updateLikes(ResponseBody likesCount);

    void saveLike(String postId);

    void showLikes(String id);

    void postLike(String id);

    void setLikeListener(String id);

    void setupLikeBtn(Context context, String postId);

    void setupShare(String contentUrl);
}
