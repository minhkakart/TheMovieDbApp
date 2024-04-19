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
import com.minhkakart.themoviedbapplication.adapters.TVShowItemAdapter;
import com.minhkakart.themoviedbapplication.adapters.TrailerItemAdapter;
import com.minhkakart.themoviedbapplication.models.network.PaginateResponse;
import com.minhkakart.themoviedbapplication.models.network.MovieResult;
import com.minhkakart.themoviedbapplication.models.network.Result;
import com.minhkakart.themoviedbapplication.models.network.TVResult;
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
    private TVShowItemAdapter tvShowsListAdapter;
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

        initAll();

    }

    private void initAll() {
        initTrending();
        getUpcomingTrailers();
        initMoviesList();
        initTvShowsList();
    }

    private void getUpcomingTrailers() {
        RecyclerView rvUpcoming = findViewById(R.id.rvTrailers);
        TrailerItemAdapter trailerItemAdapter = new TrailerItemAdapter();

        rvUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUpcoming.setAdapter(trailerItemAdapter);

        trailerItemAdapter.setPendingLoad(true);

        Call<PaginateResponse<MovieResult>> call = tmdbApi.getMoviesList("upcoming", 1);
        call.enqueue(new Callback<PaginateResponse<MovieResult>>() {
            @Override
            public void onResponse(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Response<PaginateResponse<MovieResult>> response) {
                PaginateResponse<MovieResult> paginateResponse = response.body();
                assert paginateResponse != null;
                List<MovieResult> movieResults = paginateResponse.getResults();
                trailerBackDropPaths = movieResults.stream().map(Result::getBackdropPath).collect(Collectors.toList());
                handleChangeTrailerBgr();
                trailerItemAdapter.setMovieList(movieResults);
            }

            @Override
            public void onFailure(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Throwable t) {
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
                    getTrending("day");
                } else {
                    getTrending("week");
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
        Call<PaginateResponse<MovieResult>> call = tmdbApi.getTrendingMovie(timeWindow);
        call.enqueue(new Callback<PaginateResponse<MovieResult>>() {
            @Override
            public void onResponse(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Response<PaginateResponse<MovieResult>> response) {
                PaginateResponse<MovieResult> paginateResponse = response.body();
                assert paginateResponse != null;
                trendingListAdapter.update(paginateResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Throwable t) {
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
        Call<PaginateResponse<MovieResult>> call = tmdbApi.getMoviesList(keyword, 1);
        call.enqueue(new Callback<PaginateResponse<MovieResult>>() {
            @Override
            public void onResponse(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Response<PaginateResponse<MovieResult>> response) {
                PaginateResponse<MovieResult> paginateResponse = response.body();
                assert paginateResponse != null;
                moviesListAdapter.update(paginateResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<PaginateResponse<MovieResult>> call, @NonNull Throwable t) {
                Log.e("MoviesList", "Error: " + t.getMessage());
            }
        });
    }

    private void initTvShowsList() {
        RecyclerView rvTvShowsList = findViewById(R.id.rvTVsList);
        rvTvShowsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvShowsListAdapter = new TVShowItemAdapter();
        rvTvShowsList.setAdapter(tvShowsListAdapter);

        TabLayout tlTvShowsList = findViewById(R.id.tlTVsList);
        tlTvShowsList.addOnTabSelectedListener(new CustomOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getTvShowsList("popular");
                } else if (tab.getPosition() == 1) {
                    getTvShowsList("on_the_air");
                } else if (tab.getPosition() == 2) {
                    getTvShowsList("airing_today");
                } else {
                    getTvShowsList("top_rated");
                }
            }
        });

        getTvShowsList("popular");

    }

    private void getTvShowsList(String keyword) {
        tvShowsListAdapter.setPendingLoad(true);
        Call<PaginateResponse<TVResult>> call = tmdbApi.getTVsList(keyword, 1);
        call.enqueue(new Callback<PaginateResponse<TVResult>>() {
            @Override
            public void onResponse(@NonNull Call<PaginateResponse<TVResult>> call, @NonNull Response<PaginateResponse<TVResult>> response) {
                PaginateResponse<TVResult> paginateResponse = response.body();
                assert paginateResponse != null;
                tvShowsListAdapter.update(paginateResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<PaginateResponse<TVResult>> call, @NonNull Throwable t) {
                Log.e("TvShowsList", "Error: " + t.getMessage());
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