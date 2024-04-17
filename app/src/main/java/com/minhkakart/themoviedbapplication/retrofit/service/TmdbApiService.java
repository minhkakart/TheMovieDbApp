package com.minhkakart.themoviedbapplication.retrofit.service;

import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TmdbApiService {

    @GET("trending/movie/{time_window}?language=vi-VN")
    Call<TrendingMovieResponse> getTrendingMovie(@Path("time_window") String timeWindow);

}
