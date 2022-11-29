package com.example.movieapplicationretrofitroommvvm.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.movieapplicationretrofitroommvvm.ApplicationDatabase;
import com.example.movieapplicationretrofitroommvvm.model.Images;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.model.ResultVideo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class RepositoryMovie {
    public ArrayList<Result> listMovie = new ArrayList<>();
    public ArrayList<Result> listFavorite = new ArrayList<>();
    public ArrayList<Result> mySearchList = new ArrayList<>();
    public ArrayList<Result> recipeAlreadyFavory = new ArrayList<>();
    public ArrayList<ResultVideo> videoList = new ArrayList<>();
    public String hyperLink = "";

    private RepositoryMovie(){}
    private static RepositoryMovie INSTANCE = null;
    public static RepositoryMovie getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RepositoryMovie();
        }
        return INSTANCE ;
    }

    public ArrayList<ResultVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<ResultVideo> videoList) {
        this.videoList = videoList;
    }

    public ArrayList<Result> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Result> listMovie) {
        this.listMovie = listMovie;
    }

    public ArrayList<Result> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Result> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }

    public ArrayList<Result> getRecipeAlreadyFavory() {
        return recipeAlreadyFavory;
    }

    public void setRecipeAlreadyFavory(ArrayList<Result> recipeAlreadyFavory) {
        this.recipeAlreadyFavory = recipeAlreadyFavory;
    }

    public void deleteMovieFavory(Result result){
        listFavorite.remove(result);
    }

    public boolean isFavorite(Result movieSelected){
        boolean result=  false;
        for(Result resultFav: listFavorite){
            if(resultFav.getRelease_date().equals(movieSelected.getRelease_date())){
                result = true;
            }
        }
        return result;
    }

    public boolean choiceYear(String textSearch) {
        boolean res = false;
        for (Result result : listMovie) {
            if (result.getRelease_date().toLowerCase().contains(textSearch)) {
                res = true;
                mySearchList.add(result);
            }
        }
        return res;
    }

    public LiveData<List<Result>> getFavoriteMovies (Context context){
        return ApplicationDatabase.getInstance(context).getResultDao().getFavMovies();
    }

    public void delete(Result result, Context context){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ApplicationDatabase.getInstance(context).getResultDao().deleteFavori(result);
            }
        });
    }


    public void addToListFavorite(Result result, Context context){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ApplicationDatabase.getInstance(context).getResultDao().insertFavori(result);
            }
        });
    }

    public String toCreateVideoHyperlinkById(String variable){
        int pos =0;
        for(ResultVideo resultVideo1 :RepositoryMovie.getInstance().videoList) {
            if(resultVideo1.getSite().equalsIgnoreCase(variable)){
                pos+=pos;
                resultVideo1.getId();

                Log.d("id",resultVideo1.getId());
                hyperLink= "https://www.youtube.com/watch?v="+resultVideo1.getKey();
                Log.d("pos", String.valueOf(pos));

            }
        }

        return hyperLink;
    }







}
