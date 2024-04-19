package com.minhkakart.themoviedbapplication.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Person extends BaseEntity {
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("also_known_as")
    @Expose
    public List<String> alsoKnownAs;
    @SerializedName("biography")
    @Expose
    public String biography;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("deathday")
    @Expose
    public Object deathday;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("known_for_department")
    @Expose
    public String knownForDepartment;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("place_of_birth")
    @Expose
    public String placeOfBirth;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;

    public boolean isAdult() {
        return adult;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public Object getDeathday() {
        return deathday;
    }

    public int getGender() {
        return gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
