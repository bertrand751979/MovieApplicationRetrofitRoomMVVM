package com.example.movieapplicationretrofitroommvvm;


import com.example.movieapplicationretrofitroommvvm.model.Root;
import com.example.movieapplicationretrofitroommvvm.model.RootVideo;

import java.lang.reflect.Parameter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MovieRetrofitApi {
    public interface MyMovieRetrofitService {
        @GET("search/movie")
        Call<Root> getRoot(@Query("api_key")String api_key,@Query("query")String query);

        @GET("movie/{movie_id}/videos")
         Call<RootVideo> getRootVids(@Path ("movie_id")Integer movie_id,@Query("api_key")String api_key);
    }
    private final static String BASE_URL="https://api.themoviedb.org/3/";

    private static MovieRetrofitApi INSTANCE = null;
    private MovieRetrofitApi(){}
    public static MovieRetrofitApi getInstance(){
        if (INSTANCE == null){
            INSTANCE = new MovieRetrofitApi();
        }
        return INSTANCE;
    }
    public Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
