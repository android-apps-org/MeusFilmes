package com.jdemaagd.meusfilmes.network;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.common.AppConstants;
import com.jdemaagd.meusfilmes.models.ApiResponse;
import com.jdemaagd.meusfilmes.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

    private Application mApplication;
    private MutableLiveData<List<Movie>> mMovies;
    private MutableLiveData<Movie> mMovie;

    private TMDBClient apiClient;

    public MovieRepository(Application application) {
        mApplication = application;
        apiClient = TMDBService.createService(TMDBClient.class);
    }

    public LiveData<List<Movie>> getSortedMovies(String sortDescriptor) {
        mMovies = new MutableLiveData<>();
        loadMovies(sortDescriptor, 1);

        return mMovies;
    }

    private void loadMovies(String sortDescriptor, int page) {
        Call<ApiResponse<Movie>> call = null;

        if (sortDescriptor.equals(AppConstants.POPULARITY_SORT_DESCRIPTOR)) {
            call = apiClient.getPopularMovies(
                    mApplication.getString(R.string.language),
                    String.valueOf(1));
        }
        if (sortDescriptor.equals(AppConstants.TOP_RATED_SORT_DESCRIPTOR)) {
            call = apiClient.getTopRatedMovies(
                    mApplication.getString(R.string.language),
                    String.valueOf(1));
        }

        call.enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call,
                                   Response<ApiResponse<Movie>> response) {
                if (response.isSuccessful()) {
                    List<Movie> result = response.body().results;
                    List<Movie> value = mMovies.getValue();
                    if (value == null || value.isEmpty()) {
                        mMovies.setValue(result);
                    } else {
                        value.addAll(result);
                        mMovies.setValue(value);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
                Log.d(LOG_TAG, "MovieRepository.loadMovies: network error.");
                mMovies = null;
            }
        });
    }
}
