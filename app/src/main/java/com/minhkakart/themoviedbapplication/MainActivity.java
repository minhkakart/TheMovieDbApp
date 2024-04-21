package com.minhkakart.themoviedbapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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
import com.minhkakart.themoviedbapplication.models.network.Result;
import com.minhkakart.themoviedbapplication.tmdb.TmdbApi;
import com.minhkakart.themoviedbapplication.tmdb.TmdbImageUrlGetter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
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
                    .load(TmdbImageUrlGetter.getBackdropMediumUrl(trailerBackDropPaths.get(pos)))
                    .error(R.drawable.image_load_failed)
                    .into(ivTrailerBgr, new PicassoCallBack());
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
        TmdbApi.getMoviesList("upcoming", 1, (responseBody, t) -> {
            if (t != null) {
                Log.e("Upcoming", "Error: " + t.getMessage(), t);
                return;
            }
            if (responseBody != null) {
                trailerItemAdapter.setMovieList(responseBody.getResults());
                trailerBackDropPaths = responseBody.getResults().stream().map(Result::getBackdropPath).collect(Collectors.toList());
                handleChangeTrailerBgr();
            } else {
                Log.e("Upcoming", "Error: body is null");
            }
        });
    }

    private void initTrending() {
        ViewGroup trendingSection = findViewById(R.id.trending_section);
        Button btnSeeAllTrending = trendingSection.findViewById(R.id.btnSeeAll);
        btnSeeAllTrending.setVisibility(Button.GONE);
        TextView tvTitle = trendingSection.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.trending_label);
        TabLayout tlTrending = trendingSection.findViewById(R.id.tlMovies);
        View v = Objects.requireNonNull(tlTrending.getTabAt(0)).getCustomView();
        tlTrending.removeAllTabs();
        tlTrending.addTab(tlTrending.newTab().setText("Today").setCustomView(v), true);
        tlTrending.addTab(tlTrending.newTab().setText("This Week").setCustomView(v));
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
        RecyclerView rvTrending = trendingSection.findViewById(R.id.rvMovies);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trendingListAdapter = new MovieItemAdapter();
        rvTrending.setAdapter(trendingListAdapter);
        trendingListAdapter.setPendingLoad(true);
        getTrending("day");
    }

    private void getTrending(String timeWindow) {
        trendingListAdapter.setPendingLoad(true);
        TmdbApi.getTrendingMovie(timeWindow, (responseBody, t) -> {
            if (t != null) {
                Log.e("Trending", "Error: " + t.getMessage(), t);
                return;
            }
            if (responseBody != null) {
                trendingListAdapter.update(responseBody.getResults());
            } else {
                Log.e("Trending", "Error: body is null");
            }
        });
    }

    private void initMoviesList() {
        ViewGroup moviesSection = findViewById(R.id.all_movie_section);
        TextView tvTitle = moviesSection.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.movies);
        RecyclerView rvMoviesList = moviesSection.findViewById(R.id.rvMovies);
        rvMoviesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        moviesListAdapter = new MovieItemAdapter();
        rvMoviesList.setAdapter(moviesListAdapter);
        Button btnSeeAllMovies = moviesSection.findViewById(R.id.btnSeeAll);
        btnSeeAllMovies.setOnClickListener(v -> {
            Intent seeAllMovies = new Intent(this, AllMovieActivity.class);
            startActivity(seeAllMovies);
        });
        TabLayout tlMoviesList = moviesSection.findViewById(R.id.tlMovies);
        View v = Objects.requireNonNull(tlMoviesList.getTabAt(0)).getCustomView();
        tlMoviesList.removeAllTabs();
        tlMoviesList.addTab(tlMoviesList.newTab().setText("Popular").setCustomView(v));
        tlMoviesList.addTab(tlMoviesList.newTab().setText("Now Playing").setCustomView(v));
        tlMoviesList.addTab(tlMoviesList.newTab().setText("Upcoming").setCustomView(v));
        tlMoviesList.addTab(tlMoviesList.newTab().setText("Top Rated").setCustomView(v));
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
        TmdbApi.getMoviesList(keyword, 1, (responseBody, t) -> {
            if (t != null) {
                Log.e("MoviesList", "Error: " + t.getMessage(), t);
                return;
            }
            if (responseBody != null) {
                moviesListAdapter.update(responseBody.getResults());
            } else {
                Log.e("MoviesList", "Error: body is null");
            }
        });
    }

    private void initTvShowsList() {
        ViewGroup tvShowsSection = findViewById(R.id.tv_show_section);
        TextView tvTitle = tvShowsSection.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_shows);
        RecyclerView rvTvShowsList = tvShowsSection.findViewById(R.id.rvMovies);
        rvTvShowsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvShowsListAdapter = new TVShowItemAdapter();
        rvTvShowsList.setAdapter(tvShowsListAdapter);
        TabLayout tlTvShowsList = tvShowsSection.findViewById(R.id.tlMovies);
        View v = Objects.requireNonNull(tlTvShowsList.getTabAt(0)).getCustomView();
        tlTvShowsList.removeAllTabs();
        tlTvShowsList.addTab(tlTvShowsList.newTab().setText("Popular").setCustomView(v));
        tlTvShowsList.addTab(tlTvShowsList.newTab().setText("On The Air").setCustomView(v));
        tlTvShowsList.addTab(tlTvShowsList.newTab().setText("Airing Today").setCustomView(v));
        tlTvShowsList.addTab(tlTvShowsList.newTab().setText("Top Rated").setCustomView(v));
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

        TmdbApi.getTVsList(keyword, 1, (responseBody, t) -> {
            if (t != null) {
                Log.e("TVShowsList", "Error: " + t.getMessage(), t);
                return;
            }
            if (responseBody != null) {
                tvShowsListAdapter.update(responseBody.getResults());
            } else {
                Log.e("TVShowsList", "Error: body is null");
            }
        });
    }

    private void handleChangeTrailerBgr() {
        handler.postDelayed(changeTrailerBgr, 0);
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