package com.example.movieapplicationretrofitroommvvm.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;

import java.util.List;

public class MovieFavoriteFragmentViewModel extends ViewModel {
    public LiveData<List<Result>> getFavoriteList(Context context){
        return RepositoryMovie.getInstance().getFavoriteMovies(context);
    }

    public void deleteMovieFavorite(Result result,Context context){
        RepositoryMovie.getInstance().delete(result,context);
    }

}
