package com.example.movieapplicationretrofitroommvvm.fragments;

import static com.example.movieapplicationretrofitroommvvm.activities.SearchMovieActivity.MOVIE_EXTRA;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplicationretrofitroommvvm.OnImgDeleteClickedAction;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.activities.DetailActivity;
import com.example.movieapplicationretrofitroommvvm.adapters.FavoryAdapter;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;
import com.example.movieapplicationretrofitroommvvm.viewModels.MovieFavoriteFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoryFragment extends Fragment {
    private ArrayList<Result> displayFavory = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavoryAdapter favoryAdapter;
    private MovieFavoriteFragmentViewModel movieFavoriteFragmentViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Favoris: ");
        movieFavoriteFragmentViewModel = new ViewModelProvider(this).get(MovieFavoriteFragmentViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_favorite_movie,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.recycler_favorite_movie);
        setViewItem();
    }



    private void setViewItem(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        OnLinearClickedAction onLinearClickedAction = new OnLinearClickedAction() {
            @Override
            public void goToDescription(Result result) {
                Intent intent = new Intent(FavoryFragment.this.getContext(), DetailActivity.class);
                intent.putExtra(MOVIE_EXTRA,result);
                startActivity(intent);
                Toast.makeText(FavoryFragment.this.getContext(), "Vers Description", Toast.LENGTH_SHORT).show();
            }
        };

        OnImgDeleteClickedAction onImgDeleteClickedAction = new OnImgDeleteClickedAction() {
            @Override
            public void deleteMovie(Result result) {
                movieFavoriteFragmentViewModel.deleteMovieFavorite(result,getContext());

                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_SHORT).show();
            }
        };

        favoryAdapter = new FavoryAdapter(onLinearClickedAction, onImgDeleteClickedAction);
        recyclerView.setAdapter(favoryAdapter);


        movieFavoriteFragmentViewModel.getFavoriteList(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                favoryAdapter.setListFavoryAdapter(new ArrayList<>(results));
                if (results.size() == 0) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    EmptyFavoryListFragment llf = new EmptyFavoryListFragment();
                    ft.replace(R.id.fragment_container, llf);
                    ft.commit();
                }
                RepositoryMovie.getInstance().recipeAlreadyFavory = (ArrayList<Result>) results;
            }
        });
    }
}
