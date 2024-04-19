package com.minhkakart.themoviedbapplication.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minhkakart.themoviedbapplication.models.ImageAppendToResponse;
import com.minhkakart.themoviedbapplication.models.VideoAppendToResponse;

public class BaseEntity {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("videos")
    @Expose
    protected VideoAppendToResponse videoAppendToResponse;
    @SerializedName("images")
    @Expose
    protected ImageAppendToResponse imageAppendToResponse;

    public int getId() {
        return id;
    }

    public VideoAppendToResponse getVideoAppendToResponse() {
        return videoAppendToResponse;
    }

    public ImageAppendToResponse getImageAppendToResponse() {
        return imageAppendToResponse;
    }
}
