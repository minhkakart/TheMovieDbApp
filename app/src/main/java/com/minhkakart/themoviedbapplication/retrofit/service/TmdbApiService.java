package com.minhkakart.themoviedbapplication.retrofit.service;

import com.minhkakart.themoviedbapplication.retrofit.models.MovieDetail;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApiService {
    @GET("trending/movie/{time_window}?language=vi-VN")
    Call<TrendingMovieResponse> getTrendingMovie(@Path("time_window") String timeWindow);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId, @Query("append_to_response") String appendToResponse);

    @GET("movie/{keyword}?language=vi-VN")
    Call<TrendingMovieResponse> getMoviesList(@Path("keyword") String keyword, @Query("page") int page);
}
