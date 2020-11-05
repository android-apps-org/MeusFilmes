package com.jdemaagd.meusfilmes.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jdemaagd.meusfilmes.models.Movie;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            MovieDatabase.class, "meusfilmes")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
