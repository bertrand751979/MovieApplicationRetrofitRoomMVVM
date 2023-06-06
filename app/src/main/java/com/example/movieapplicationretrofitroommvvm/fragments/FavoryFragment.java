package com.example.movieapplicationretrofitroommvvm.fragments;

import static android.content.ContentValues.TAG;
import static com.example.movieapplicationretrofitroommvvm.activities.DetailActivity.db;
import static com.example.movieapplicationretrofitroommvvm.activities.SearchMovieActivity.MOVIE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.TypeConverters;

import com.example.movieapplicationretrofitroommvvm.OnImgDeleteClickedAction;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.activities.DetailActivity;
import com.example.movieapplicationretrofitroommvvm.adapters.FavoryAdapter;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoryFragment extends Fragment {
    private ArrayList<Result> displayFavory = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavoryAdapter favoryAdapter;
    private Result result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Favoris: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        RepositoryMovie.getInstance().getListFavorite().clear();
        toGetFavList();

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
                toDeleteMyMovies(result);
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_SHORT).show();
            }

        };
        favoryAdapter = new FavoryAdapter(RepositoryMovie.getInstance().listFavorite, onLinearClickedAction, onImgDeleteClickedAction);
        favoryAdapter.setListFavoryAdapter(RepositoryMovie.getInstance().listFavorite);
        recyclerView.setAdapter(favoryAdapter);
    }

    private void toDeleteMyMovies(Result res){
       db.collection("results").document(res.getDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        RepositoryMovie.getInstance().listFavorite.remove(res);
                        //RepositoryMovie.getInstance().getListFavorite().clear();
  //                      toGetFavList();
 //                       RepositoryMovie.getInstance().toGetFavList((Adapter) favoryAdapter);
                        favoryAdapter.setListFavoryAdapter(RepositoryMovie.getInstance().listFavorite);
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    public void toGetFavList(){
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
                                Log.d("Liste", String.valueOf(RepositoryMovie.getInstance().listFavorite.size()));
                            }
                            Toast.makeText(getContext(), "size get listFav : "+RepositoryMovie.getInstance().listFavorite.size(), Toast.LENGTH_SHORT).show();
                            favoryAdapter.setListFavoryAdapter(RepositoryMovie.getInstance().listFavorite);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
