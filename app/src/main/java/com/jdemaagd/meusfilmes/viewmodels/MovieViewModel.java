package com.jdemaagd.meusfilmes.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jdemaagd.meusfilmes.data.MovieRepository;
import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    private LiveData<List<Movie>> mMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        mMovieRepository = new MovieRepository(application.getApplicationContext());
        mMovies = mMovieRepository.getMovies();
    }

    public void addMovie(Movie movie) {
        mMovieRepository.addMovie(movie);
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public void removeMovie(Movie movie) {
        mMovieRepository.removeMovie(movie);
    }

    public void resetTable() {
        mMovieRepository.resetMovieTable();
    }
}
