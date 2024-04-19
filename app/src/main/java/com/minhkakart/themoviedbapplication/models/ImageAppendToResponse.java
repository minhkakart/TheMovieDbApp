package com.minhkakart.themoviedbapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageAppendToResponse {

    @SerializedName("backdrops")
    @Expose
    private List<Object> backdrops;
    @SerializedName("logos")
    @Expose
    private List<Object> logos;
    @SerializedName("posters")
    @Expose
    private List<Object> posters;
    @SerializedName("profiles")
    @Expose
    private List<ImageProfile> profiles;

    public List<Object> getBackdrops() {
        return backdrops;
    }

    public List<Object> getLogos() {
        return logos;
    }

    public List<Object> getPosters() {
        return posters;
    }

    public List<ImageProfile> getProfiles() {
        return profiles;
    }
}
