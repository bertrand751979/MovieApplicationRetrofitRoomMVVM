package com.example.movieapplicationretrofitroommvvm.viewHolders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView vhMoviePoster;
    private TextView  vhMovieTitle;
    private TextView  vhMovieOverview;
    private LinearLayout linearLayout;


    public MovieViewHolder(@NonNull View view) {
        super(view);
        vhMoviePoster = view.findViewById(R.id.raw_poster);
        vhMovieTitle = view.findViewById(R.id.raw_title);
        vhMovieOverview = view.findViewById(R.id.raw_overview);
        linearLayout = view.findViewById(R.id.raw_linear_movie);
    }

    public ImageView getVhMoviePoster() {
        return vhMoviePoster;
    }

    public void setVhMoviePoster(ImageView vhMoviePoster) {
        this.vhMoviePoster = vhMoviePoster;
    }

    public TextView getVhMovieTitle() {
        return vhMovieTitle;
    }

    public void setVhMovieTitle(TextView vhMovieTitle) {
        this.vhMovieTitle = vhMovieTitle;
    }

    public TextView getVhMovieOverview() {
        return vhMovieOverview;
    }

    public void setVhMovieOverview(TextView vhMovieOverview) {
        this.vhMovieOverview = vhMovieOverview;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void bind (Result result, OnLinearClickedAction onLinearClickedAction){
        String photo = "https://image.tmdb.org/t/p/w500"+result.getPoster_path();
       if (photo==null){
           vhMoviePoster.setImageResource(R.drawable.ic_movie);
       }else{
           Glide.with(vhMoviePoster.getContext())
                        .load(photo)
                                .into(vhMoviePoster);}
        Log.d("img",(photo));
        vhMovieTitle.setText(result.getOriginal_title());
        vhMovieOverview.setText(result.getOverview());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onLinearClickedAction.goToDescription(result);
            }
        });
    }
}
