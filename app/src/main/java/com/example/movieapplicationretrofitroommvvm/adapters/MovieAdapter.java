package com.example.movieapplicationretrofitroommvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.viewHolders.MovieViewHolder;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private ArrayList<Result> listAdapter = new ArrayList<>();
    private OnLinearClickedAction onLinearClickedAction;

    public MovieAdapter(OnLinearClickedAction onLinearClickedAction) {
        this.onLinearClickedAction = onLinearClickedAction;
    }

    public void setListAdapter(ArrayList<Result> listAdapter) {
        this.listAdapter = listAdapter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_movies,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(listAdapter.get(position), onLinearClickedAction);
    }

    @Override
    public int getItemCount() {
        return listAdapter.size();
    }
}
