package com.gentech.anton.ifunny.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anton on 12.07.16.
 */
public class Content implements Parcelable {

    private String id;
    private String title;
    private String url;
    private Integer viewCount;
    private Integer likeCount;
    private Integer contentType;

    public Content() {
    }

    public Content(String id, String title, String url,
                   Integer viewCount, Integer likeCount, Integer contentType) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.viewCount = viewCount == null ? Integer.valueOf(0) : viewCount;
        this.likeCount = likeCount == null ? Integer.valueOf(0) : likeCount;
        this.contentType = contentType;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

        Content that = (Content) o;

        if (!id.equals(that.id)) return false;
        if (!title.equals(that.title)) return false;
        if (!url.equals(that.url)) return false;
        if (!viewCount.equals(that.viewCount)) return false;
        if (!likeCount.equals(that.likeCount)) return false;
        return contentType.equals(that.contentType);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + viewCount.hashCode();
        result = 31 * result + likeCount.hashCode();
        result = 31 * result + contentType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", contentType=" + contentType +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeInt(viewCount);
        parcel.writeInt(likeCount);
        parcel.writeInt(contentType);

    }

    protected Content(Parcel in) {
        id = in.readString();
        title = in.readString();
        url = in.readString();
        viewCount = in.readInt();
        likeCount = in.readInt();
        contentType = in.readInt();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
