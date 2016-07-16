package com.gentech.anton.ifunny.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 12.07.16.
 */

public class BaseModel {

    public String id;
    public String title;
    public Integer type;
    public String url;
    public String date;
    public String modifyDate;
    public String img;
    public String fullContent;
    public List<String> images = new ArrayList<String>();
    public List<Object> videos = new ArrayList<Object>();
    public Series series;
    public Integer views;
    public Integer countComment;
    public String keywords;

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", img='" + img + '\'' +
                ", fullContent='" + fullContent + '\'' +
                ", images=" + images +
                ", videos=" + videos +
                ", series=" + series +
                ", views=" + views +
                ", countComment=" + countComment +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}