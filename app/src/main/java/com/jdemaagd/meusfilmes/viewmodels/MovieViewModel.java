package com.jdemaagd.meusfilmes.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jdemaagd.meusfilmes.data.AppDatabase;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.network.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieViewModel.class.getSimpleName();

    private MovieRepository mMovieRepository;

    // cache movies
    private LiveData<List<Movie>> mFavMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(application);
        mFavMovies = database.movieDao().getAllMovies();

        mMovieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public LiveData<List<Movie>> getSortedMovies(String sortDescriptor) {
        return mMovieRepository.getSortedMovies(sortDescriptor);
    }
}
