package com.example.movieapplicationretrofitroommvvm.activities;

import static android.content.ContentValues.TAG;
import static com.example.movieapplicationretrofitroommvvm.activities.SearchMovieActivity.MOVIE_EXTRA;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movieapplicationretrofitroommvvm.MovieRetrofitApi;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.adapters.FavoryAdapter;
import com.example.movieapplicationretrofitroommvvm.fragments.FavoryFragment;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.model.ResultVideo;
import com.example.movieapplicationretrofitroommvvm.model.RootVideo;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;
import com.example.movieapplicationretrofitroommvvm.viewModels.DetailActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ImageView descPoster;
    private TextView descDetail;
    private ImageView descFavory;
    private TextView descReleaseDate;
    private TextView descVoteCount;
    private TextView descPopularity;
    private TextView descTitle;
    private Result result;
    private ResultVideo resultVideo;
    private Button descVids;
    private DetailActivityViewModel detailActivityViewModel;
    private String videoHyperlinkCreation ="";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailActivityViewModel = new ViewModelProvider(this).get(DetailActivityViewModel.class);
        result = (Result) getIntent().getSerializableExtra(MOVIE_EXTRA);
        setTitle("Description");
        descPoster = findViewById(R.id.desc_poster);
        descDetail = findViewById(R.id.desc_movie_detail);
        descFavory = findViewById(R.id.desc_img_fav);
        descReleaseDate = findViewById(R.id.desc_release_date);
        descVoteCount = findViewById(R.id.desc_movie_vote_count);
        descPopularity = findViewById(R.id.desc_movie_popularity);
        descTitle = findViewById(R.id.desc_movie_title);
        descVids = findViewById(R.id.desc_btn_video);

        descVids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceVideo();
            }
        });
        String photo = "https://image.tmdb.org/t/p/w500" + result.getPoster_path();
        Glide.with(this)
                .load(photo)
                .into(descPoster);
        descDetail.setText(result.getOverview());
        descReleaseDate.setText(result.getRelease_date());
        descVoteCount.setText(String.valueOf(result.getVote_count()));
        descPopularity.setText(String.valueOf(result.getPopularity()));
        descTitle.setText(result.getOriginal_title());
        descFavory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              descFavory.setImageResource(R.drawable.ic_star_full);
                addMovieToFirabase();
                //refreshFavoriteList();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        RepositoryMovie.getInstance().listFavorite2.clear();
        toGetFavList();
    }


    public void callServiceVideo() {
        MovieRetrofitApi.MyMovieRetrofitService service = MovieRetrofitApi.getInstance().getClient().create(MovieRetrofitApi.MyMovieRetrofitService.class);
        Call<RootVideo> calls = service.getRootVids(Integer.valueOf(result.getId()), "aba6bb2b364392551140adc1bc89437b");
        Log.d("id", String.valueOf(result.getId()));
        calls.enqueue(new Callback<RootVideo>() {
            @Override
            public void onResponse(Call<RootVideo> call, Response<RootVideo> response) {
                if (response.code() == 200) {
                    processVideoResponse(response);
                    RepositoryMovie.getInstance().toCreateVideoHyperlinkById("youtube");
                    toDisplayVideo();
                }
            }
            @Override
            public void onFailure(Call<RootVideo> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Echec", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processVideoResponse(Response<RootVideo> response) {
        if ((response.body().getResults() != null)&&(response.body().getResults().size()>0)){
            RepositoryMovie.getInstance().videoList = (ArrayList<ResultVideo>) response.body().getResults();
            Toast.makeText(this, "Taille de List Result OK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Result null", Toast.LENGTH_SHORT).show();
        }
    }

    public void toCreateVideoHyperlink(){
        for(ResultVideo resultVideo :RepositoryMovie.getInstance().videoList){
            if(resultVideo.getSite().equalsIgnoreCase("Youtube")){
                videoHyperlinkCreation = "https://www.youtube.com/watch?v="+resultVideo.getKey();
            }else{
                Toast.makeText(this, "Pas de video créés", Toast.LENGTH_SHORT).show();
            }
        }
        //toDisplayVideo();
    }

    public void toDisplayVideo(){
        if(RepositoryMovie.getInstance().toCreateVideoHyperlinkById("youtube") != null){
            Intent intent = new Intent(DetailActivity.this, VideoActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Pas de video à montrer", Toast.LENGTH_SHORT).show();
        }
    }
   /* public String toCreateVideoHyperlinkById(){
        for(ResultVideo resultVideo :RepositoryMovie.getInstance().videoList) {
            if(resultVideo.getSite().equalsIgnoreCase("Youtube")){
                resultVideo.getId();
                Log.d("id",resultVideo.getId());
                videoHyperlinkCreation = "https://www.youtube.com/watch?v="+resultVideo.getKey();
            }
        }
        return videoHyperlinkCreation;
    }*/

   /* private void checkIfFavoriteMovie() {
        if (RepositoryMovie.getInstance().isFavorite(result) == true) {
            descFavory.setImageResource(R.drawable.ic_star_full);
        } else {
            descFavory.setImageResource(R.drawable.ic_star_empty);
        }
    }*/

    private void refreshFavoriteList() {
        detailActivityViewModel.getFavoriteList(DetailActivity.this).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                RepositoryMovie.getInstance().listFavorite = (ArrayList<Result>) results;
            }
        });
    }

   /* private void setOnTimeToFavorite() {
        detailActivityViewModel.getFavoriteList(DetailActivity.this).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                RepositoryMovie.getInstance().listFavorite = (ArrayList<Result>) results;
                if (RepositoryMovie.getInstance().isFavorite(result) == true) {

                    Toast.makeText(DetailActivity.this, "Deja Favori", Toast.LENGTH_SHORT).show();
                } else {
                    detailActivityViewModel.addToFavorite(result, DetailActivity.this);
                   // db.collection("results");
                }
            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
        }
        finish();
        return super.onOptionsItemSelected(item);
    }


    public void addMovieToFirabase() {
        if(setIfFavoriteOrNotAction(result)==true){
            Toast.makeText(this, "Retrouvé dans la liste des favoris", Toast.LENGTH_SHORT).show();
            descFavory.setImageResource(R.drawable.ic_star_full);
            return;
        }
        //si donc le film est dans les favoris on affiche un message et on quitte la fonction
        Map<List,Result> favoryMovie = new HashMap<>();
        favoryMovie = new HashMap<>();
        favoryMovie.put(RepositoryMovie.getInstance().getListMovie(), result);
        // Add a new document with a generated ID
        db.collection("results")
                .add(result)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("resultat",documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Une erreure",e);
                    }
                });
    }
    //Reccuperer la liste des favoris
    public void toGetFavList(){
        db.collection("results")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Result results = new Result();
                                //c'est l'id tjrs type string de l'enregistrement firestore
                                results.setDocumentId(document.getId());
                                Log.d("rang", document.getId());
                                //l'id de l'objet result int est dans le data
                                results.setPoster_path((String) document.getData().get("poster_path"));
                                results.setOverview((String) document.getData().get("overview"));
                                results.setOriginal_title((String) document.getData().get("original_title"));
                                RepositoryMovie.getInstance().listFavorite2.add(results);
                            }
                            //Log.d("myListeFav", String.valueOf(RepositoryMovie.getInstance().listFavorite2.size()));
                            settingStarIfFavoriteMovie(result);
                            Toast.makeText(DetailActivity.this, "Nombre de favoris : "+RepositoryMovie.getInstance().listFavorite2.size(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    //verifier s'il est dans les favoris
    private boolean setIfFavoriteOrNotAction(Result detailRes){
        boolean check= false;
            for(Result result2:RepositoryMovie.getInstance().getListFavorite2()){
                if(result2.getOverview().equals(detailRes.getOverview())){
                    check =true;
                }
            }
        return check;
    }

    // Configurer Les etoiles
    private void settingStarIfFavoriteMovie(Result favResult) {
        if(setIfFavoriteOrNotAction(favResult)){
            descFavory.setImageResource(R.drawable.ic_star_full);
        } else {
            descFavory.setImageResource(R.drawable.ic_star_empty);
        }
    }

}
