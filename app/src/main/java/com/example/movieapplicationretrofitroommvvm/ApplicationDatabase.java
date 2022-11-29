package com.example.movieapplicationretrofitroommvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movieapplicationretrofitroommvvm.model.Result;


@Database(entities = {Result.class},version = 3)
//@TypeConverters({VolumeInfoConverter.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    private static ApplicationDatabase INSTANCE;
    public abstract ResultDao getResultDao();
    public static synchronized ApplicationDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class, "result_app").build();
        }
        return INSTANCE;
    }
}
