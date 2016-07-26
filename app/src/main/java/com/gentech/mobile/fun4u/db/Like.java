package com.gentech.mobile.fun4u.db;

/**
 * Created by anton on 26.07.16.
 */
public class Like {
    private String postId;
    private int isLiked;

    public Like() {
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

        if (isLiked != like.isLiked) return false;
        return postId != null ? postId.equals(like.postId) : like.postId == null;

    }

    @Override
    public int hashCode() {
        int result = postId != null ? postId.hashCode() : 0;
        result = 31 * result + isLiked;
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "postId='" + postId + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }
}
