package com.minhkakart.themoviedbapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.minhkakart.themoviedbapplication.activities.AllMovieActivity;
import com.minhkakart.themoviedbapplication.adapters.MovieItemAdapter;
import com.minhkakart.themoviedbapplication.adapters.TrailerItemAdapter;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResponse;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResult;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingResult;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbApiService;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbImageApiService;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final TmdbApiService tmdbApi = TmdbService.getTmdbInstance();
    private MovieItemAdapter trendingListAdapter, moviesListAdapter;
    private ImageView ivTrailerBgr;
    private static List<String> trailerBackDropPaths = new ArrayList<>();
    private static Handler handler;
    private static Thread changeTrailerBgr;
    private static final long LOOP_DELAY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivTrailerBgr = findViewById(R.id.ivTrailerBgr);

        handler = new Handler(Looper.getMainLooper());
        changeTrailerBgr = new Thread(() -> {
            int count = trailerBackDropPaths.size();
            int pos = new Random().nextInt(count - 1);
            Picasso.get()
                    .load(TmdbImageApiService.getBackdropMediumUrl(trailerBackDropPaths.get(pos)))
                    .error(R.drawable.image_load_failed)
                    .into(ivTrailerBgr, new PicassoCallBack());
        });

        Button btnSeeAllMovies = findViewById(R.id.btnSeeAllMovies);
        btnSeeAllMovies.setOnClickListener(v -> {
            Intent seeAllMovies = new Intent(this, AllMovieActivity.class);
            startActivity(seeAllMovies);
        });

        initTrending();
        getUpcomingTrailers();
        initMoviesList();

    }

    private void getUpcomingTrailers() {
        RecyclerView rvUpcoming = findViewById(R.id.rvTrailers);
        TrailerItemAdapter trailerItemAdapter = new TrailerItemAdapter();

        rvUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUpcoming.setAdapter(trailerItemAdapter);

        trailerItemAdapter.setPendingLoad(true);

        Call<TrendingMovieResponse> call = tmdbApi.getMoviesList("upcoming", 1);
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrendingMovieResponse> call, @NonNull Response<TrendingMovieResponse> response) {
                TrendingMovieResponse trendingMovieResponse = response.body();
                assert trendingMovieResponse != null;
                List<TrendingMovieResult> movieResults = trendingMovieResponse.getResults();
                trailerBackDropPaths = movieResults.stream().map(TrendingResult::getBackdropPath).collect(Collectors.toList());
                handleChangeTrailerBgr();
                trailerItemAdapter.setMovieList(movieResults);
            }

            @Override
            public void onFailure(@NonNull Call<TrendingMovieResponse> call, @NonNull Throwable t) {
                Log.e("Upcoming", "Error: " + t.getMessage());
            }
        });
    }

    private void initTrending() {
        TabLayout tlTrending = findViewById(R.id.tlTrending);
        tlTrending.addOnTabSelectedListener(new CustomOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    getTrending("week");
                } else {
                    getTrending("day");
                }
            }
        });

        RecyclerView rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trendingListAdapter = new MovieItemAdapter();
        rvTrending.setAdapter(trendingListAdapter);
        trendingListAdapter.setPendingLoad(true);
        getTrending("day");
    }

    private void getTrending(String timeWindow) {
        Call<TrendingMovieResponse> call = tmdbApi.getTrendingMovie(timeWindow);
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrendingMovieResponse> call, @NonNull Response<TrendingMovieResponse> response) {
                TrendingMovieResponse trendingMovieResponse = response.body();
                assert trendingMovieResponse != null;
                trendingListAdapter.update(trendingMovieResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<TrendingMovieResponse> call, @NonNull Throwable t) {
                Log.e("Trending", "Error: " + t.getMessage());
            }
        });
    }

    private void initMoviesList() {
        RecyclerView rvMoviesList = findViewById(R.id.rvMoviesList);
        rvMoviesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        moviesListAdapter = new MovieItemAdapter();
        rvMoviesList.setAdapter(moviesListAdapter);

        TabLayout tlMoviesList = findViewById(R.id.tlMoviesList);
        tlMoviesList.addOnTabSelectedListener(new CustomOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getMoviesList("popular");
                } else if (tab.getPosition() == 1) {
                    getMoviesList("now_playing");
                } else if (tab.getPosition() == 2) {
                    getMoviesList("upcoming");
                } else {
                    getMoviesList("top_rated");
                }
            }
        });

        tlMoviesList.selectTab(tlMoviesList.getTabAt(1), true);

    }

    private void getMoviesList(String keyword) {
        moviesListAdapter.setPendingLoad(true);
        Call<TrendingMovieResponse> call = tmdbApi.getMoviesList(keyword, 1);
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrendingMovieResponse> call, @NonNull Response<TrendingMovieResponse> response) {
                TrendingMovieResponse trendingMovieResponse = response.body();
                assert trendingMovieResponse != null;
                moviesListAdapter.update(trendingMovieResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<TrendingMovieResponse> call, @NonNull Throwable t) {
                Log.e("MoviesList", "Error: " + t.getMessage());
            }
        });
    }

    private void handleChangeTrailerBgr() {
        handler.postDelayed(changeTrailerBgr, LOOP_DELAY);
    }

    private static class PicassoCallBack implements com.squareup.picasso.Callback {
        @Override
        public void onSuccess() {
            MainActivity.handler.postDelayed(MainActivity.changeTrailerBgr, LOOP_DELAY);
        }

        @Override
        public void onError(Exception e) {
            Log.e("PicassoCallBack", "Error: ", e);
        }
    }

    private static abstract class CustomOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public abstract void onTabSelected(TabLayout.Tab tab);

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            // Do nothing
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            // Do nothing
        }
    }

}