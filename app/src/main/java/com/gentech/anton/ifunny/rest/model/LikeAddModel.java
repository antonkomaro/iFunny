package com.gentech.anton.ifunny.rest.model;

/**
 * Created by anton on 16.07.16.
 */
public class LikeAddModel {
    public Object name;
    public Object message;
    public Object code;
    public Integer status;

    @Override
    public String toString() {
        return "LikeAddModel{" +
                "name=" + name +
                ", message=" + message +
                ", code=" + code +
                ", status=" + status +
                '}';
    }
}
