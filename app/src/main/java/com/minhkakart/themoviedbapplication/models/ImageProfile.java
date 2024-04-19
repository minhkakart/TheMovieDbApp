package com.minhkakart.themoviedbapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageProfile {
    @SerializedName("aspect_ratio")
    @Expose
    public float aspectRatio;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("iso_639_1")
    @Expose
    public Object iso6391;
    @SerializedName("file_path")
    @Expose
    public String filePath;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("width")
    @Expose
    public int width;
}
