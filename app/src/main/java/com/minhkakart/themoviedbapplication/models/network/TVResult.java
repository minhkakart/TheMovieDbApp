package com.minhkakart.themoviedbapplication.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TVResult extends Result {
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry;

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public Date getReleaseDateAsDate() throws ParseException {
        return parseDate(firstAirDate);
    }

    private Date parseDate(String releaseDate) throws ParseException {
        String pattern = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        return dateFormat.parse(releaseDate);
    }
}