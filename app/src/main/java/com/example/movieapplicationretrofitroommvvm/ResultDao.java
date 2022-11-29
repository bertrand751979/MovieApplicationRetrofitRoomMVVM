package com.example.movieapplicationretrofitroommvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapplicationretrofitroommvvm.model.Result;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM Result ")
    LiveData<List<Result>> getFavMovies();

    @Insert
    void insertFavori(Result result);

    @Delete
    void deleteFavori(Result result);

}
