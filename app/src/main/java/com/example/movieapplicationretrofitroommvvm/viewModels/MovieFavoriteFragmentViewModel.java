package com.example.movieapplicationretrofitroommvvm.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieFavoriteFragmentViewModel extends ViewModel {
   /* public LiveData<List<Result>> getFavoriteList(Context context){
        return RepositoryMovie.getInstance().getFavoriteMovies(context);
    }*/

    private MutableLiveData<List<Result>> myFavoriteList = new MutableLiveData<>();
    public LiveData<List<Result>> favoryLiveData = myFavoriteList;

    public void deleteMovieFavorite(Result result,Context context){
        RepositoryMovie.getInstance().delete(result,context);
    }

    public void toPostMyItemsFavoriteList(){
        myFavoriteList.postValue(RepositoryMovie.getInstance().getListFavorite());
    }


}
