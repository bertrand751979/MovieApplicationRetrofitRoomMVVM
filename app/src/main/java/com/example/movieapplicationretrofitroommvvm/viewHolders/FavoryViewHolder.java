package com.example.movieapplicationretrofitroommvvm.viewHolders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplicationretrofitroommvvm.OnImgDeleteClickedAction;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;

public class FavoryViewHolder extends RecyclerView.ViewHolder {
    private ImageView vhFavoryMoviePoster;
    private TextView vhFavoryMovieTitle;
    private TextView vhFavoryMovieOverview;
    private LinearLayout linearLayout;
    private ImageView vhDelete;


    public FavoryViewHolder(@NonNull View view) {
        super(view);
        vhFavoryMoviePoster = view.findViewById(R.id.raw_fav_poster);
        vhFavoryMovieTitle = view.findViewById(R.id.raw_fav_title);
        vhFavoryMovieOverview = view.findViewById(R.id.raw_fav_overview);
        linearLayout = view.findViewById(R.id.raw_fav_linear);
        vhDelete = view.findViewById(R.id.raw_fav_delete);
    }

    public ImageView getVhFavoryMoviePoster() {
        return vhFavoryMoviePoster;
    }

    public void setVhFavoryMoviePoster(ImageView vhFavoryMoviePoster) {
        this.vhFavoryMoviePoster = vhFavoryMoviePoster;
    }

    public TextView getVhFavoryMovieTitle() {
        return vhFavoryMovieTitle;
    }

    public void setVhFavoryMovieTitle(TextView vhFavoryMovieTitle) {
        this.vhFavoryMovieTitle = vhFavoryMovieTitle;
    }

    public TextView getVhFavoryMovieOverview() {
        return vhFavoryMovieOverview;
    }

    public void setVhFavoryMovieOverview(TextView vhFavoryMovieOverview) {
        this.vhFavoryMovieOverview = vhFavoryMovieOverview;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void bind(Result result, OnLinearClickedAction onLinearClickedAction, OnImgDeleteClickedAction onImgDeleteClickedAction) {
        String photo = "https://image.tmdb.org/t/p/w500" + result.getPoster_path();

            Glide.with(vhFavoryMoviePoster.getContext())
                    .load(photo)
                    .error(R.drawable.ic_movie)
                    //image par defaut le temps de chargement
                    .placeholder(R.drawable.ic_movie)
                    .into(vhFavoryMoviePoster);

        Log.d("img", (photo));
        vhFavoryMovieTitle.setText(result.getOriginal_title());
        vhFavoryMovieOverview.setText(result.getOverview());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLinearClickedAction.goToDescription(result);
            }
        });
        vhDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImgDeleteClickedAction.deleteMovie(result);
            }
        });
    }
}
