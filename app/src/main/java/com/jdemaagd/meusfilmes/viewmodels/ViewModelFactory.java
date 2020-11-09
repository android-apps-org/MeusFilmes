package com.jdemaagd.meusfilmes.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jdemaagd.meusfilmes.data.AppDatabase;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mAppDatabase;
    private final int mMovieId;

    public ViewModelFactory(AppDatabase appDatabase, int movieId) {
        mAppDatabase = appDatabase;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mAppDatabase, mMovieId);
    }
}
