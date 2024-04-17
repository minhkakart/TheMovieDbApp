package com.minhkakart.themoviedbapplication.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("backdrops")
    @Expose
    private List<Object> backdrops;
    @SerializedName("logos")
    @Expose
    private List<Object> logos;
    @SerializedName("posters")
    @Expose
    private List<Object> posters;

    public List<Object> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Object> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Object> getLogos() {
        return logos;
    }

    public void setLogos(List<Object> logos) {
        this.logos = logos;
    }

    public List<Object> getPosters() {
        return posters;
    }

    public void setPosters(List<Object> posters) {
        this.posters = posters;
    }
}
