package com.gentech.anton.ifunny.interfaces;

import com.gentech.anton.ifunny.models.Content;

import java.util.List;

/**
 * Created by anton on 18.07.16.
 */
public interface UpdateListener {
    void updateAdapter(List<Content> contentList);
}
