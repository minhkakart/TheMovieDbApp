package com.minhkakart.themoviedbapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;

}
