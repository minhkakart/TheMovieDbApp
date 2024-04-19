package com.minhkakart.themoviedbapplication.retrofit.service;

import com.minhkakart.themoviedbapplication.models.entities.Movie;
import com.minhkakart.themoviedbapplication.models.entities.TVSeries;
import com.minhkakart.themoviedbapplication.models.network.PaginateResponse;
import com.minhkakart.themoviedbapplication.models.network.MovieResult;
import com.minhkakart.themoviedbapplication.models.network.TVResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApiService {
    // Movie
    @GET("trending/movie/{time_window}?language=vi-VN")
    Call<PaginateResponse<MovieResult>> getTrendingMovie(@Path("time_window") String timeWindow);
    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetail(@Path("movie_id") int movieId, @Query("append_to_response") String appendToResponse);
    @GET("movie/{keyword}?language=vi-VN")
    Call<PaginateResponse<MovieResult>> getMoviesList(@Path("keyword") String keyword, @Query("page") int page);

    // TV
    @GET("trending/tv/{time_window}?language=vi-VN")
    Call<PaginateResponse<TVResult>> getTrendingTV(@Path("time_window") String timeWindow);

    @GET("tv/{tv_id}")
    Call<TVSeries> getTVDetail(@Path("tv_id") int tvId, @Query("append_to_response") String appendToResponse);

    @GET("tv/{keyword}?language=vi-VN")
    Call<PaginateResponse<TVResult>> getTVsList(@Path("keyword") String keyword, @Query("page") int page);

}
