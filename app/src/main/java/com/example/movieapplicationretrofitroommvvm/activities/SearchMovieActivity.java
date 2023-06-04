package com.example.movieapplicationretrofitroommvvm.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapplicationretrofitroommvvm.MovieRetrofitApi;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.model.ResultVideo;
import com.example.movieapplicationretrofitroommvvm.model.Root;
import com.example.movieapplicationretrofitroommvvm.model.RootVideo;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchMovieActivity extends AppCompatActivity {
    private Button btnSearch;
    public  EditText editTextSearch;
    public static String MOVIE_EXTRA = "movie_extra";
    //public static String VIDEO_EXTRA = "video_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        btnSearch = findViewById(R.id.btn_search);
        LinearLayout myLayout = findViewById(R.id.main_layout);
        editTextSearch = findViewById(R.id.edit_search_movie);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //Log.i("touchEvent","Touch is detected");
                int evenType = event.getActionMasked();
                switch(evenType)
                {
                    case MotionEvent.ACTION_UP:
                        closeKeyboard();
                        Log.i("touchEvent","Action up");
                        //if(editTextSearch.getText().toString()!=null) {
                            //InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(SearchMovieActivity.this.INPUT_METHOD_SERVICE);
                            //inputManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                       // }
                        break;
                }
                return true;
            }
        });



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSearch.getText().toString().equals("")){
                    Toast.makeText(SearchMovieActivity.this, "Zone de saisie vide", Toast.LENGTH_SHORT).show();
                }else {
                    callService();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public void callService() {
        MovieRetrofitApi.MyMovieRetrofitService service = MovieRetrofitApi.getInstance().getClient().create(MovieRetrofitApi.MyMovieRetrofitService.class);
        Call<Root> call = service.getRoot("aba6bb2b364392551140adc1bc89437b", editTextSearch.getText().toString());
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.code() == 200) {
                    processResponse(response);

                }
                Toast.makeText(SearchMovieActivity.this, "OK", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SearchMovieActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void processResponse(Response<Root> response) {
        if(response.body().getResults().size()>0){
            RepositoryMovie.getInstance().listMovie= (ArrayList<Result>) response.body().getResults();
            Intent intent = new Intent(SearchMovieActivity.this,MainActivity.class);
            startActivity(intent);

            //Toast.makeText(this, "Liste Display", Toast.LENGTH_SHORT).show();
        }
    }
}
