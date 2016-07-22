package com.gentech.mobile.fun4u.interfaces;

import com.squareup.okhttp.ResponseBody;

/**
 * Created by anton on 19.07.16.
 */
public interface ActionsListener {
    void updateLikes(ResponseBody likesCount);
}
