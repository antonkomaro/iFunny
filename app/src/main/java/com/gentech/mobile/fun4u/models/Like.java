package com.gentech.mobile.fun4u.models;

/**
 * Created by anton on 22.07.16.
 */
public class Like {
    private int id;
    private String postId;
    private int isLiked;

    public Like() {
    }

    public Like(int id, String postId, int isLiked) {
        this.id = id;
        this.postId = postId;
        this.isLiked = isLiked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (id != like.id) return false;
        if (isLiked != like.isLiked) return false;
        return postId.equals(like.postId);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + postId.hashCode();
        result = 31 * result + isLiked;
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", postId='" + postId + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }
}
