package com.jdemaagd.meusfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jdemaagd.meusfilmes.common.AppConstants;
import com.jdemaagd.meusfilmes.common.AppDatabase;
import com.jdemaagd.meusfilmes.common.AppUtils;
import com.jdemaagd.meusfilmes.databinding.ActivityDetailsMovieBinding;
import com.jdemaagd.meusfilmes.models.ApiResponse;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.common.AppExecutor;
import com.jdemaagd.meusfilmes.models.api.Review;
import com.jdemaagd.meusfilmes.models.api.Video;
import com.jdemaagd.meusfilmes.network.TMDBClient;
import com.jdemaagd.meusfilmes.network.TMDBService;
import com.jdemaagd.meusfilmes.viewmodels.MovieDetailsViewModel;
import com.jdemaagd.meusfilmes.viewmodels.ViewModelFactory;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private TMDBClient mApiClient;
    private AppDatabase mAppDatabase;
    private ActivityDetailsMovieBinding mBinding;
    private boolean mIsFav;
    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_movie);
        mBinding.setLifecycleOwner(this);

        Intent intent = getIntent();

        if (intent != null) {
            mApiClient = TMDBService.createService(TMDBClient.class);
            mAppDatabase = AppDatabase.getInstance(getApplicationContext());
            mMovieId = intent.getIntExtra(AppConstants.EXTRA_MOVIE_ID, 0);

            mIsFav = false;
            // TODO: reviews and trailers to view
            // ../id/reviews
            // ../id/videos
            //loadReviews();
            //loadTrailers();

            loadMovie(mApiClient, mMovieId);
        }
    }

    private void bindFavIcon(Movie movie) {
        mBinding.ivFavorite.setOnClickListener((view) -> {
            AppExecutor.getInstance().diskIO().execute(() -> {
                if (mIsFav) {
                    Log.d(LOG_TAG, "Remove movie from database via Room.");
                    mAppDatabase.movieDao().removeMovie(movie);
                } else {
                    Log.d(LOG_TAG, "Insert movie into database via Room.");
                    mAppDatabase.movieDao().addMovie(movie);
                }
                runOnUiThread(() -> setFavIcon());
            });
        });
    }

    private void loadMovie(TMDBClient apiClient, int movieId) {
        if(AppConstants.IS_NETWORK_CONNECTED) {
            Call<Movie> call = apiClient.getMovieById(movieId);

            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    try {
                        Movie movie = response.body();
                        Log.d(LOG_TAG, "Synopsis: " + movie.getSynopsis());

                        mBinding.tvErrorMessage.setText(response.body().getSynopsis());
                        mBinding.tvOriginalTitle.setText(movie.getOriginalTitle());
                        mBinding.tvReleaseYear.setText(movie.getReleaseDate().substring(0, 4));
                        mBinding.tvDuration.setText(movie.getDuration() + " mins");
                        mBinding.tvVoteAverage.setText(String.valueOf(movie.getUserRating()));
                        mBinding.tvOverview.setText(movie.getSynopsis());

                        Picasso.get()
                                .load(AppConstants.POSTER_URL + movie.getPosterPath())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.error)
                                .into(mBinding.ivPosterThumb);

                        bindFavIcon(movie);
                        setFavIcon();
                    } catch (NullPointerException e) {
                        Log.d(LOG_TAG, "MovieDetailsActivity.loadMovie: network error.");
                        Toast.makeText(MovieDetailsActivity.this,
                                getString(R.string.error_message), Toast.LENGTH_LONG).show();
                        showErrorMessage();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.d(LOG_TAG, "MovieDetailsActivity.loadMovie: network error.");
                    Toast.makeText(MovieDetailsActivity.this,
                            getString(R.string.error_message), Toast.LENGTH_LONG).show();
                    showErrorMessage();
                }
            });
        } else {
            Toast.makeText(MovieDetailsActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            showErrorMessage();
        }
    }

    private void loadReviews() {
        if (AppUtils.getInstance().isNetworkAvailable(this)) {
            Call<ApiResponse<Review>> call = mApiClient.getReviews(mMovieId);

        } else {
            Toast.makeText(MovieDetailsActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTrailers() {
        if (AppUtils.getInstance().isNetworkAvailable(this)) {
            Call<ApiResponse<Video>> call = mApiClient.getVideos(mMovieId);

        } else {
            Toast.makeText(MovieDetailsActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void setFavIcon() {
        ViewModelFactory factory = new ViewModelFactory(mAppDatabase, mMovieId);
        final MovieDetailsViewModel movieDetailsViewModel = new ViewModelProvider(this, factory).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.getMovie().observe(this, favMovie -> {
            // movieDetailsViewModel.getMovie().removeObserver(this);
            Log.d(LOG_TAG, "Receiving LiveData Update.");
            if (favMovie == null) {
                mIsFav = false;
                mBinding.ivFavorite.setImageResource(R.drawable.ic_heart_border_pink_24dp);
            } else {
                mIsFav = true;
                mBinding.ivFavorite.setImageResource(R.drawable.ic_heart_pink_24dp);
            }
        });
    }

    private void showErrorMessage() {
        mBinding.ivFavorite.setVisibility(View.INVISIBLE);
        mBinding.ivPosterThumb.setVisibility(View.INVISIBLE);
        mBinding.tvDuration.setVisibility(View.INVISIBLE);
        mBinding.tvOriginalTitle.setVisibility(View.INVISIBLE);
        mBinding.tvOverview.setVisibility(View.INVISIBLE);
        mBinding.tvReleaseYear.setVisibility(View.INVISIBLE);
        mBinding.tvVoteAverage.setVisibility(View.INVISIBLE);

        mBinding.tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
