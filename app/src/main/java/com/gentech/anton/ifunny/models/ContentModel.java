package com.gentech.anton.ifunny.models;

import com.gentech.anton.ifunny.utils.ContentType;

/**
 * Created by anton on 12.07.16.
 */
public class ContentModel {
    private Integer id;
    private String title;
    private String url;
    private Integer viewCount;
    private Integer likeCount;
    private ContentType contentType;

    public ContentModel() {
    }

    public ContentModel(Integer id, String title, String url, Integer viewCount, Integer likeCount, ContentType contentType) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentModel that = (ContentModel) o;

        if (!id.equals(that.id)) return false;
        if (!title.equals(that.title)) return false;
        if (!url.equals(that.url)) return false;
        return contentType == that.contentType;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + contentType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ContentModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", contentType=" + contentType +
                '}';
    }
}
