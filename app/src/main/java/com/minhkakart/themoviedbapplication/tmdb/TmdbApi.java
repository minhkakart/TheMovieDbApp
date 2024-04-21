package com.minhkakart.themoviedbapplication.tmdb;


import androidx.annotation.NonNull;

import com.minhkakart.themoviedbapplication.models.entities.Movie;
import com.minhkakart.themoviedbapplication.models.entities.TVSeries;
import com.minhkakart.themoviedbapplication.models.network.MovieResult;
import com.minhkakart.themoviedbapplication.models.network.PaginateResponse;
import com.minhkakart.themoviedbapplication.models.network.TVResult;
import com.minhkakart.themoviedbapplication.tmdb.service.TmdbApiService;
import com.minhkakart.themoviedbapplication.tmdb.service.TmdbService;

import retrofit2.Call;
import retrofit2.Callback;

public class TmdbApi {
    private static final TmdbApiService tmdbApiService = TmdbService.getTmdbInstance();

    public static void getTrendingMovie(String timeWindow, TmdbApi.ICallBack<PaginateResponse<MovieResult>> callback) {
        Call<PaginateResponse<MovieResult>> call = tmdbApiService.getTrendingMovie(timeWindow);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    public static void getMoviesList(String keyword, int page, TmdbApi.ICallBack<PaginateResponse<MovieResult>> callback) {
        Call<PaginateResponse<MovieResult>> call = tmdbApiService.getMoviesList(keyword, page);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    public static void getMovieDetail(int movieId, String appendToResponse, TmdbApi.ICallBack<Movie> callback) {
        Call<Movie> call = tmdbApiService.getMovieDetail(movieId, appendToResponse);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    public static void getTrendingTV(String timeWindow, TmdbApi.ICallBack<PaginateResponse<TVResult>> callback) {
        Call<PaginateResponse<TVResult>> call = tmdbApiService.getTrendingTV(timeWindow);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    public static void getTVsList(String keyword, int page, TmdbApi.ICallBack<PaginateResponse<TVResult>> callback) {
        Call<PaginateResponse<TVResult>> call = tmdbApiService.getTVsList(keyword, page);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    public static void getTVDetail(int tvId, String appendToResponse, TmdbApi.ICallBack<TVSeries> callback) {
        Call<TVSeries> call = tmdbApiService.getTVDetail(tvId, appendToResponse);
        call.enqueue(new TmdbApi.CallBack<>(callback));
    }

    /**
     * Callback for retrofit
     *
     * @param <T> response body type
     */
    public static class CallBack<T> implements Callback<T> {
        private final ICallBack<T> callBack;

        protected CallBack(ICallBack<T> callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull retrofit2.Response<T> response) {
            callBack.onFinished(response.body(), null);
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            callBack.onFinished(null, t);
        }
    }

    /**
     * Interface for callback
     *
     * @param <T> response body type
     */
    public interface ICallBack<T> {

        /**
         * Callback when request finished
         *
         * @param responseBody response body
         * @param t            throwable if error occurred
         */
        void onFinished(T responseBody, Throwable t);
    }

}
