package com.example.movieapplicationretrofitroommvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieapplicationretrofitroommvvm.OnImgDeleteClickedAction;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.viewHolders.FavoryViewHolder;

import java.util.ArrayList;

public class FavoryAdapter extends RecyclerView.Adapter<FavoryViewHolder> {
    private ArrayList<Result> listFavoryAdapter = new ArrayList<>();
    private OnLinearClickedAction onLinearActionClicked;
    private OnImgDeleteClickedAction onImgDeleteClickedAction;

    public FavoryAdapter(OnLinearClickedAction onLinearActionClicked, OnImgDeleteClickedAction onImgDeleteClickedAction) {
        this.onLinearActionClicked = onLinearActionClicked;
        this.onImgDeleteClickedAction = onImgDeleteClickedAction;
    }

    public void setListFavoryAdapter(ArrayList<Result> listFavoryAdapter) {
        this.listFavoryAdapter = listFavoryAdapter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_fav_movies,parent,false);
        return new FavoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoryViewHolder holder, int position) {
        holder.bind(listFavoryAdapter.get(position),onLinearActionClicked,onImgDeleteClickedAction);
    }

    @Override
    public int getItemCount() {
        return listFavoryAdapter.size();
    }
}
