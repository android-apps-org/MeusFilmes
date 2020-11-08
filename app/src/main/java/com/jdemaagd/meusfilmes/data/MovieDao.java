package com.jdemaagd.meusfilmes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void addMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE id = :movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM movie ORDER BY popularity")
    LiveData<List<Movie>> getMovies();

    @Delete
    void removeMovie(Movie movie);

    @Query("DELETE FROM movie")
    void resetTable();

}
