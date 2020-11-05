package com.jdemaagd.meusfilmes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY :sortDescriptor")
    LiveData<List<Movie>> getAllMovies(String sortDescriptor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void remove(Movie movie);

    @Query("DELETE FROM movie")
    void resetTable();
}
