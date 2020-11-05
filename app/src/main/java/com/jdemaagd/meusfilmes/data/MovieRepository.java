package com.jdemaagd.meusfilmes.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

public class MovieRepository {

    private Context mContext;

    public MovieRepository(Context context) {
        mContext = context.getApplicationContext();
    }

    public void addMovie(Movie movie) {
        AsyncTask.execute(() -> MovieDatabase.getDatabase(mContext).movieDao().insertMovie(movie));
        // TODO: replace with Executor?

        /**
             AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    MovieDatabase.getDatabase(mContext).movieDao().insertMovie(movie);
                }
             });
         */

    }

    public LiveData<List<Movie>> getMovies() {
        return MovieDatabase.getDatabase(mContext).movieDao().getAllMovies("popularity");
        // TODO: Preferences for sort
    }

    public void removeMovie(Movie movie) {
        AsyncTask.execute(() -> MovieDatabase.getDatabase(mContext).movieDao().remove(movie));
        // TODO: replace with Executor?
    }

    public void resetMovieTable() {
        AsyncTask.execute(() -> MovieDatabase.getDatabase(mContext).movieDao().resetTable());
        // TODO: replace with Executor?
    }
}
