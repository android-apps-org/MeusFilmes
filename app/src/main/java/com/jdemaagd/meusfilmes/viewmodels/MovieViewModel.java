package com.jdemaagd.meusfilmes.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jdemaagd.meusfilmes.data.AppDatabase;
import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieViewModel.class.getSimpleName();

    // cache movies
    private LiveData<List<Movie>> mMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        mMovies = database.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return mMovies;
    }
}
