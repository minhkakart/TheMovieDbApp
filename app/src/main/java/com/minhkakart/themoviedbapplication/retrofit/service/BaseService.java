package com.minhkakart.themoviedbapplication.retrofit.service;

import com.minhkakart.themoviedbapplication.configure.EnvironmentVariable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {

    private static final HttpLoggingInterceptor sLogging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder sHttpClient =
            new OkHttpClient.Builder().addInterceptor( chain -> {
                Request original = chain.request().newBuilder().addHeader("Authorization", "Bearer " + EnvironmentVariable.AUTH_TOKEN).build();
                return chain.proceed(original);
            });

    static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        if (!sHttpClient.interceptors().contains(sLogging)) {
            sHttpClient.addInterceptor(sLogging);
            builder.client(sHttpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
