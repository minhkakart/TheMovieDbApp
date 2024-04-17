package com.minhkakart.themoviedbapplication.retrofit.service;

import com.minhkakart.themoviedbapplication.configure.EnvironmentVariable;

public class TmdbService extends BaseService {
    private static TmdbApiService tmdbApi;
    public static TmdbApiService getTmdbInstance() {
        if (tmdbApi == null) {
            tmdbApi = createService(TmdbApiService.class, EnvironmentVariable.BASE_URL);
        }
        return tmdbApi;
    }

}
