package com.minhkakart.themoviedbapplication.retrofit.models.trending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingResult {
    @SerializedName("backdrop_path")
    @Expose
    protected String backdropPath;
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("overview")
    @Expose
    protected String overview;
    @SerializedName("poster_path")
    @Expose
    protected String posterPath;
    @SerializedName("media_type")
    @Expose
    protected String mediaType;
    @SerializedName("adult")
    @Expose
    protected boolean adult;
    @SerializedName("original_language")
    @Expose
    protected String originalLanguage;
    @SerializedName("genre_ids")
    @Expose
    protected List<Integer> genreIds;
    @SerializedName("popularity")
    @Expose
    protected float popularity;

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public float getPopularity() {
        return popularity;
    }
}