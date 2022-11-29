package com.example.movieapplicationretrofitroommvvm.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;

import java.util.List;


public class MovieDisplayFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Result>>myMovieMutableList = new MutableLiveData<>();
    public LiveData<List<Result>> movieLiveData = myMovieMutableList;

    public void toPostMyItemsList(){
        myMovieMutableList.postValue(RepositoryMovie.getInstance().getListMovie());
    }

}
