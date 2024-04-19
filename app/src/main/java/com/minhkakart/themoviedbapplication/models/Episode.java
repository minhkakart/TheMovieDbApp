package com.minhkakart.themoviedbapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("vote_average")
    @Expose
    public int voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episode_number")
    @Expose
    public int episodeNumber;
    @SerializedName("episode_type")
    @Expose
    public String episodeType;
    @SerializedName("production_code")
    @Expose
    public String productionCode;
    @SerializedName("runtime")
    @Expose
    public Object runtime;
    @SerializedName("season_number")
    @Expose
    public int seasonNumber;
    @SerializedName("show_id")
    @Expose
    public int showId;
    @SerializedName("still_path")
    @Expose
    public Object stillPath;
}