package com.example.movieapplicationretrofitroommvvm.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;

import java.util.List;

public class DetailActivityViewModel extends ViewModel {
    public void addToFavorite(Result result,Context context){
        RepositoryMovie.getInstance().addToListFavorite(result, context);
    }

    public LiveData<List<Result>> getFavoriteList(Context context){
        return RepositoryMovie.getInstance().getFavoriteMovies(context);
    }

}
