package com.minhkakart.themoviedbapplication.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minhkakart.themoviedbapplication.models.KnownFor;

import java.util.List;

public class PeopleResult extends Result {
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("known_for_department")
    @Expose
    private String knownForDepartment;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("known_for")
    @Expose
    private List<KnownFor> knownFor;

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }
}