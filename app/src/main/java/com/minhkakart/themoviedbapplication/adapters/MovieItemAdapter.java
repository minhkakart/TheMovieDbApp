package com.minhkakart.themoviedbapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.minhkakart.themoviedbapplication.R;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResult;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbImageApiService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder>{

    private List<TrendingMovieResult> trendingMovieResults = new ArrayList<>();
    private static boolean pendingLoad = false;
    private static final int pendingItemCount = 5;

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<TrendingMovieResult> trendingMovieResults){
        this.trendingMovieResults = trendingMovieResults;
        pendingLoad = false;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPendingLoad(boolean pendingLoad){
        MovieItemAdapter.pendingLoad = pendingLoad;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (pendingLoad){
            holder.bindPlaceholder();
            return;
        }
        holder.bind(trendingMovieResults.get(position));
    }

    @Override
    public int getItemCount() {
        if (pendingLoad){
            return pendingItemCount;
        }
        return trendingMovieResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView bgr;
        private final TextView tvMovieName, tvReleaseDate, tvVoteAverage;
        private final CircularProgressIndicator cpiRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgr = itemView.findViewById(R.id.bgr);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            tvVoteAverage = itemView.findViewById(R.id.tvRatingValue);
            cpiRating = itemView.findViewById(R.id.cpiRating);
        }
        public void bind(TrendingMovieResult trendingMovieResult){
            Picasso.get().load(TmdbImageApiService.getPosterMediumUrl(trendingMovieResult.getPosterPath()))
                    .placeholder(R.drawable.glyphicons_basic_38_picture_grey)
                    .error(R.drawable.image_load_failed)
                    .into(bgr, new Callback() {
                        @Override
                        public void onSuccess() {
                            showInfo();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

            tvMovieName.setText(trendingMovieResult.getTitle());
            float voteAverage = ((int) (trendingMovieResult.getVoteAverage() * 10)) / (float) 10;
            if (voteAverage == 0 && trendingMovieResult.getVoteCount() == 0) {
                tvVoteAverage.setText("N/R");
                cpiRating.setProgress(0);
            } else if (voteAverage < 2.5) {
                tvVoteAverage.setTextColor(tvVoteAverage.getResources().getColor(R.color.red, null));
                cpiRating.setIndicatorColor(tvVoteAverage.getResources().getColor(R.color.red, null));
            } else if (voteAverage < 5) {
                tvVoteAverage.setTextColor(tvVoteAverage.getResources().getColor(R.color.orange, null));
                cpiRating.setIndicatorColor(tvVoteAverage.getResources().getColor(R.color.orange, null));
            } else if (voteAverage < 7.5) {
                tvVoteAverage.setTextColor(tvVoteAverage.getResources().getColor(R.color.yellow, null));
                cpiRating.setIndicatorColor(tvVoteAverage.getResources().getColor(R.color.yellow, null));
            } else {
                tvVoteAverage.setTextColor(tvVoteAverage.getResources().getColor(R.color.tmdbLightGreen, null));
                cpiRating.setIndicatorColor(tvVoteAverage.getResources().getColor(R.color.tmdbLightGreen, null));
            }

            tvVoteAverage.setText(String.valueOf(voteAverage));
            cpiRating.setProgress((int) (voteAverage * 10));
            try {
                String displayDate = SimpleDateFormat.getDateInstance().format(trendingMovieResult.getReleaseDateAsDate());
                tvReleaseDate.setText(displayDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public void bindPlaceholder(){
            bgr.setImageResource(R.drawable.glyphicons_basic_38_picture_grey);
            hideInfo();
        }

        public void hideInfo() {
            tvMovieName.setVisibility(View.GONE);
            tvReleaseDate.setVisibility(View.GONE);
            tvVoteAverage.setVisibility(View.GONE);
            cpiRating.setVisibility(View.GONE);
        }

        public void showInfo() {
            tvMovieName.setVisibility(View.VISIBLE);
            tvReleaseDate.setVisibility(View.VISIBLE);
            tvVoteAverage.setVisibility(View.VISIBLE);
            cpiRating.setVisibility(View.VISIBLE);
        }
    }
}
