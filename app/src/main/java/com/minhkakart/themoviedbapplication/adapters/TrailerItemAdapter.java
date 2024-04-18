package com.minhkakart.themoviedbapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhkakart.themoviedbapplication.MainActivity;
import com.minhkakart.themoviedbapplication.R;
import com.minhkakart.themoviedbapplication.configure.EnvironmentVariable;
import com.minhkakart.themoviedbapplication.retrofit.models.MovieDetail;
import com.minhkakart.themoviedbapplication.retrofit.models.VideoResult;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResult;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbImageApiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerItemAdapter extends RecyclerView.Adapter<TrailerItemAdapter.TrailerItemViewHolder> {
    private List<TrendingMovieResult> movieList = new ArrayList<>();
    private boolean pendingLoad = false;
    private static final int pendingItemCount = 5;

    @SuppressLint("NotifyDataSetChanged")
    public void setPendingLoad(boolean pendingLoad) {
        this.pendingLoad = pendingLoad;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovieList(List<TrendingMovieResult> movieList) {
        this.movieList = movieList;
        pendingLoad = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerItemAdapter.TrailerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_trailer_item, parent, false);
        return new TrailerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerItemAdapter.TrailerItemViewHolder holder, int position) {
        if (pendingLoad) {
            holder.bindPlaceholder();
            return;
        }
        TrendingMovieResult movieResult = movieList.get(position);
        holder.bind(movieResult);
    }

    @Override
    public int getItemCount() {
        if (pendingLoad) {
            return pendingItemCount;
        }
        return movieList.size();
    }

    public static class TrailerItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivTrailer, ivPlay;
        private final TextView tvTrailerName, tvTrailerDesc;

        public TrailerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTrailer = itemView.findViewById(R.id.iv_trailer);
            tvTrailerName = itemView.findViewById(R.id.tv_trailer_title);
            tvTrailerDesc = itemView.findViewById(R.id.tv_trailer_description);
            ivPlay = itemView.findViewById(R.id.iv_play_icon);
        }

        public void bind(TrendingMovieResult movieResult) {
            Call<MovieDetail> movieDetailCall = MainActivity.tmdbApi.getMovieDetail(movieResult.getId(), "videos");
            movieDetailCall.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                    if (response.isSuccessful()) {
                        MovieDetail movieDetail = response.body();
                        if (movieDetail != null) {
                            Picasso.get()
                                    .load(TmdbImageApiService.getBackdropMediumUrl(movieDetail.getBackdropPath()))
                                    .placeholder(R.drawable.play_button_svgrepo_com)
                                    .error(R.drawable.image_load_failed)
                                    .into(ivTrailer, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            tvTrailerName.setText(movieDetail.getTitle());
                                            for (VideoResult videoResult : movieDetail.getVideos().getResults()) {
                                                if (videoResult.getType().equals("Trailer") && videoResult.getSite().equals("YouTube")) {
                                                    tvTrailerDesc.setText(videoResult.getName());
                                                    ivPlay.setOnClickListener(v -> {
                                                        // Open youtube
                                                        Uri uri = Uri.parse(EnvironmentVariable.YOUTUBE_URL + videoResult.getKey());
                                                        Intent openYoutube = new Intent(Intent.ACTION_VIEW, uri);
                                                        v.getContext().startActivity(openYoutube);
                                                    });
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("Picasso", "onError: ", e);
                                        }
                                    });
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable throwable) {

                }
            });
        }

        public void bindPlaceholder() {
            ivTrailer.setImageResource(R.drawable.play_button_svgrepo_com);
            clearInfo();
        }

        public void clearInfo() {
            tvTrailerName.setText("");
            tvTrailerDesc.setText("");
        }

    }
}
