package com.example.movieapplicationretrofitroommvvm.repository;

import static android.content.ContentValues.TAG;
import static com.example.movieapplicationretrofitroommvvm.activities.DetailActivity.db;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.movieapplicationretrofitroommvvm.ApplicationDatabase;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.model.ResultVideo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class RepositoryMovie {
    public ArrayList<Result> listMovie = new ArrayList<>();
    public ArrayList<Result> listFavorite = new ArrayList<>();
    public ArrayList<Result> listFavorite2 = new ArrayList<>();

    public ArrayList<Result> mySearchList = new ArrayList<>();
    public ArrayList<Result> recipeAlreadyFavory = new ArrayList<>();
    public ArrayList<ResultVideo> videoList = new ArrayList<>();
    public String hyperLink = "";

    private static final String COLLECTION_NAME = "results";
    private static final String FAVORITEMOVIE= "favorite_movie";


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

    public ArrayList<Result> getListFavorite2() {
        return listFavorite2;
    }

    public void setListFavorite2(ArrayList<Result> listFavorite2) {
        this.listFavorite2 = listFavorite2;
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
                //pos+=pos;
                ///resultVideo1.setPosition(pos);
                Log.d("id",resultVideo1.getId());
                hyperLink= "https://www.youtube.com/watch?v="+resultVideo1.getKey();
                Log.d("pos", String.valueOf(pos));
            }
        }

        return hyperLink;
    }


    public CollectionReference getResultCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void toGetFavList(Adapter adapter){
        db.collection("results")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Result result = new Result();
                                //c'est l'id tjrs type string de l'enregistrement firestore
                                result.setDocumentId(document.getId());
                                Log.d("rang",document.getId());
                                //l'id de l'objet result int est dans le data
                                result.setPoster_path((String) document.getData().get("poster_path"));
                                result.setOverview((String) document.getData().get("overview"));
                                result.setOriginal_title((String) document.getData().get("original_title"));
                                RepositoryMovie.getInstance().listFavorite.add(result);
                            }
                            //Toast.makeText(getContext(), "size get listFav : "+RepositoryMovie.getInstance().listFavorite.size(), Toast.LENGTH_SHORT).show();
                            //adapter.setListFavoryAdapter(RepositoryMovie.getInstance().listFavorite);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }






}
