package com.jdemaagd.meusfilmes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jdemaagd.meusfilmes.data.AppDatabase;
import com.jdemaagd.meusfilmes.models.Movie;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Movie> mMovie;

    public MovieDetailsViewModel(AppDatabase appDatabase, int movieId) {
        // mMovie = appDatabase.movieDao().getMovieById(movieId);
    }

    public LiveData<Movie> getMovie() {
        return mMovie;
    }
}
