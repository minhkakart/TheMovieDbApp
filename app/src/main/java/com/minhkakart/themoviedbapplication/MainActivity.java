package com.minhkakart.themoviedbapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.minhkakart.themoviedbapplication.adapters.MovieItemAdapter;
import com.minhkakart.themoviedbapplication.adapters.TrailerItemAdapter;
import com.minhkakart.themoviedbapplication.retrofit.models.MovieDetail;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResponse;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbApiService;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final TmdbApiService tmdbApi = TmdbService.getTmdbInstance();
    private MovieItemAdapter movieItemAdapter;
    private final TrailerItemAdapter trailerItemAdapter = new TrailerItemAdapter();

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

        TabLayout tlTrending = findViewById(R.id.tlTrending);
        tlTrending.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    getTrending("week");
                } else {
                    getTrending("day");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });

        RecyclerView rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        movieItemAdapter = new MovieItemAdapter();
        rvTrending.setAdapter(movieItemAdapter);

        getTrending("day");

        // Get upcoming movies
        RecyclerView rvUpcoming = findViewById(R.id.rvTrailers);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUpcoming.setAdapter(trailerItemAdapter);
        getUpcoming();

    }

    private void getUpcoming() {
        Call<TrendingMovieResponse> call = tmdbApi.getUpcomingMovie();
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrendingMovieResponse> call, @NonNull Response<TrendingMovieResponse> response) {
                TrendingMovieResponse trendingMovieResponse = response.body();
                assert trendingMovieResponse != null;
                trailerItemAdapter.setMovieList(trendingMovieResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<TrendingMovieResponse> call, @NonNull Throwable t) {
                Log.e("Upcoming", "Error: " + t.getMessage());
            }
        });
    }

    private void getTrending(String timeWindow){
        movieItemAdapter.setPendingLoad(true);
        Call<TrendingMovieResponse> call = tmdbApi.getTrendingMovie(timeWindow);
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrendingMovieResponse> call, @NonNull Response<TrendingMovieResponse> response) {
                TrendingMovieResponse trendingMovieResponse = response.body();
                assert trendingMovieResponse != null;
                movieItemAdapter.update(trendingMovieResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<TrendingMovieResponse> call, @NonNull Throwable t) {
                Log.e("Trending", "Error: " + t.getMessage());
            }
        });
    }

}