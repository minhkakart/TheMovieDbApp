package com.minhkakart.themoviedbapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhkakart.themoviedbapplication.R;
import com.minhkakart.themoviedbapplication.retrofit.models.trending.TrendingMovieResult;
import com.minhkakart.themoviedbapplication.retrofit.service.TmdbImageApiService;
import com.squareup.picasso.Picasso;

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgr = itemView.findViewById(R.id.bgr);
        }
        public void bind(TrendingMovieResult trendingMovieResult){
            Picasso.get().load(TmdbImageApiService.getPosterMediumUrl(trendingMovieResult.getPosterPath()))
                    .placeholder(R.drawable.glyphicons_basic_38_picture_grey)
                    .error(R.drawable.image_load_failed)
                    .into(bgr);
        }

        public void bindPlaceholder(){
            bgr.setImageResource(R.drawable.glyphicons_basic_38_picture_grey);
        }
    }
}
