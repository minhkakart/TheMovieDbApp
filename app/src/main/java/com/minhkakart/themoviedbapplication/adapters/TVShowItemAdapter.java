package com.minhkakart.themoviedbapplication.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.minhkakart.themoviedbapplication.R;
import com.minhkakart.themoviedbapplication.models.network.TVResult;
import com.minhkakart.themoviedbapplication.tmdb.TmdbImageUrlGetter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TVShowItemAdapter extends RecyclerView.Adapter<TVShowItemAdapter.TVShowItemViewHolder> {
    private List<TVResult> tvResults = new ArrayList<>();
    private boolean pendingLoad = false;
    private static final int pendingItemCount = 5;

    @SuppressLint("NotifyDataSetChanged")
    public void setPendingLoad(boolean pendingLoad) {
        this.pendingLoad = pendingLoad;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<TVResult> tvResults) {
        this.tvResults = tvResults;
        pendingLoad = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item_horizontal, parent, false);
        return new TVShowItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TVShowItemViewHolder holder, int position) {
        if (pendingLoad) {
            holder.bindPlaceholder();
            return;
        }
        holder.bind(tvResults.get(position));
    }

    @Override
    public int getItemCount() {
        if (pendingLoad) {
            return pendingItemCount;
        }
        return tvResults.size();
    }

    public static class TVShowItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView bgr;
        private final TextView tvMovieName, tvReleaseDate, tvVoteAverage;
        private final CircularProgressIndicator cpiRating;

        public TVShowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            bgr = itemView.findViewById(R.id.bgr);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            tvVoteAverage = itemView.findViewById(R.id.tvRatingValue);
            cpiRating = itemView.findViewById(R.id.cpiRating);
        }

        public void bind(TVResult tvResult) {
            Picasso.get().load(TmdbImageUrlGetter.getPosterMediumUrl(tvResult.getPosterPath()))
                    .placeholder(R.drawable.glyphicons_basic_38_picture_grey)
                    .error(R.drawable.image_load_failed)
                    .into(bgr, new Callback() {
                        @Override
                        public void onSuccess() {
                            showInfo();
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Picasso", "onError: ", e);
                        }
                    });

            tvMovieName.setText(tvResult.getName());
            float voteAverage = ((int) (tvResult.getVoteAverage() * 10)) / (float) 10;
            if (voteAverage == 0 && tvResult.getVoteCount() == 0) {
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
                String displayDate = SimpleDateFormat.getDateInstance().format(tvResult.getReleaseDateAsDate());
                tvReleaseDate.setText(displayDate);
            } catch (ParseException e) {
                tvReleaseDate.setText("N/A");
                Log.e("Adapter", "bind: ", e);
            }
        }

        public void bindPlaceholder() {
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
