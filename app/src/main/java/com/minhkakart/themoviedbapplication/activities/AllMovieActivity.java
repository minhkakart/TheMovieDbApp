package com.minhkakart.themoviedbapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhkakart.themoviedbapplication.R;
import com.minhkakart.themoviedbapplication.adapters.MovieItemAdapter;
import com.minhkakart.themoviedbapplication.tmdb.TmdbApi;

public class AllMovieActivity extends AppCompatActivity {
    private int page = 1, totalPages;
    private final MovieItemAdapter adapter = new MovieItemAdapter(false);
    private TextView tvPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvPageNumber = findViewById(R.id.tvPageNumber);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getMoviesByPage(page);

        Button nextButton = findViewById(R.id.btnNext);
        Button previousButton = findViewById(R.id.btnPrevious);

        nextButton.setOnClickListener(v -> nextPage());
        previousButton.setOnClickListener(v -> previousPage());

    }

    private void nextPage() {
        if (page < totalPages) {
            page++;
            getMoviesByPage(page);
        }
    }

    private void previousPage() {
        if (page > 1) {
            page--;
            getMoviesByPage(page);
        }
    }

    private void getMoviesByPage(int page) {
        if (page < 1) {
            return;
        }
        TmdbApi.getMoviesList("popular", page, (response, error) -> {
            if (error != null) {
                Log.e("AllMovieActivity", "getMoviesByPage: ", error);
                return;
            }
            if (response != null) {
                totalPages = response.getTotalPages();
                tvPageNumber.setText(String.valueOf(page));
                adapter.update(response.getResults());
            }
        });

    }

}