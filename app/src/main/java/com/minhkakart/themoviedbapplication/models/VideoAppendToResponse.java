package com.minhkakart.themoviedbapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoAppendToResponse {
    @SerializedName("results")
    @Expose
    private List<VideoResult> results;

    public List<VideoResult> getResults() {
        return results;
    }

}
