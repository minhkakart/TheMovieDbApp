package com.minhkakart.themoviedbapplication.retrofit.models.trending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingMovieResponse {
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public List<TrendingMovieResult> results;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("total_results")
    @Expose
    public int totalResults;

    public int getPage() {
        return page;
    }

    public List<TrendingMovieResult> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
