package com.gentech.anton.ifunny.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anton on 16.07.16.
 */
public class TokenModel {
    public Integer status;
    @SerializedName("access-token")
    public String accessToken;

    @Override
    public String toString() {
        return "TokenModel{" +
                "status=" + status +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}